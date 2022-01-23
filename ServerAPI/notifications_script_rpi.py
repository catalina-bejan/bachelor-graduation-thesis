from datetime import datetime,timedelta
import requests
import json
import time
from gtts import gTTS
import os

def get_current_datetime():
    # datetime object containing current date and time
    a_datetime = datetime.now()
    #formatted_datetime = a_datetime.isoformat()
    return a_datetime

def extract_seconds_current_datetime(seconds):
    # datetime object containing current date and time
    a_datetime = datetime.now()- timedelta(seconds=seconds)
    #formatted_datetime = a_datetime.isoformat()
    return a_datetime

def get_scheduler():
    scheduler = requests.get('http://192.168.0.106:8080/api/schedulers/1')
    scheduler = scheduler.json()
    return scheduler

#list of actions detected in the amount of seconds scanned by this program (activity_scan_seconds)
def get_actions_list(start,end):
    query = {'start': start,'end': end}
    actions_list = requests.get('http://192.168.0.106:8080/api/generalActions/between/id', params=query)
    actions_list = actions_list.json()
    return actions_list

#add specific time to current date
def get_current_date_specific_time(time,current_datetime):
    specific_time = datetime.strptime(time, '%H:%M:%S').time()
    current_date_specific_time = current_datetime.replace(hour=specific_time.hour, minute=specific_time.minute, second=0, microsecond=0)
    return current_date_specific_time

def simple_get_request(url):
    response = requests.get(url)
    response = response.json()
    return response

def get_request_queryparams(url,query):
    response = requests.get(url,params=query)
    response = response.json()
    return response

def post_request_queryparams(url,query,headers):
    response = requests.post(url, params=query, headers=headers)
    return response

def post_request_body(url,body,headers):
    response = requests.post(url, json = body, headers=headers)
    return response

def send_email(to,text,subject):
    headers={'Content-type':'application/json', 'Accept':'application/json'}
    mail_query = {'to': to, 'text': text, 'subject': subject}
    post_request_queryparams('http://192.168.0.106:8080/api/mail',mail_query,headers)

def get_last_action_time(action_id):
    url = 'http://192.168.0.106:8080/api/videos/lastaction/{}'.format(action_id)
    return simple_get_request(url)

def get_notification(notification_id):
    url = 'http://192.168.0.106:8080/api/notifications/{}'.format(notification_id)
    return simple_get_request(url)

def get_last_notification_tracker(notification_id):
    url = 'http://192.168.0.106:8080/api/notificationTrackers/last/{}'.format(notification_id)
    return simple_get_request(url)

def get_caretaker(id):
    url = 'http://192.168.0.106:8080/api/users/{}'.format(id)
    return simple_get_request(url)

def convert_timestamp_to_date(date):
    date = datetime.fromisoformat(date)
    #remove timezone info
    date = date.replace(tzinfo=None)
    return date

def get_hours_between(last_datetime,first_datetime):
    hours = (last_datetime-first_datetime).total_seconds() / 60/60
    return hours

def get_minutes_between(last_datetime,first_datetime):
    hours = (last_datetime-first_datetime).total_seconds() / 60
    return hours
    
def get_last_previous_action(param):
    query = {"last": param}
    url = 'http://192.168.0.106:8080/api/videos/lastpreviousaction/1'
    return get_request_queryparams(url,query)

def post_into_notification_tracker(current_datetime,notification):
    headers={'Content-type':'application/json', 'Accept':'application/json'}
    formated_timestamp = current_datetime.isoformat()
    notification_tracker_json_object = {"timestamp": formated_timestamp, "notification": notification}
    response = post_request_body('http://192.168.0.106:8080/api/notificationTrackers', notification_tracker_json_object, headers)

def delete_old_data(max_date):
    query = {'last': max_date}
    requests.delete('http://192.168.0.106:8080/api/videos/old/delete', params=query)
    requests.delete('http://192.168.0.106:8080/api/notificationTrackers/old/delete', params=query)


if __name__ == "__main__":
    #set language for voice audio notification
    language = 'en'
    
    #activity_scan_seconds = 30
    #initialize variable for period count to calculate eating time period, where period=number of seconds that the program is sleeping
    global_period_count = 0
    while True:
        #get scheduler object from db
        scheduler = get_scheduler()
        activity_scan_seconds = scheduler["activityScanSeconds"]
        print("Activity scan seconds is {}".format(activity_scan_seconds))

        #delete data older than number of hours set by caretaker
        stored_data_hours = scheduler["storedDataHours"]
        max_date = extract_seconds_current_datetime(stored_data_hours*60*60)
        print(max_date)
        delete_old_data(max_date)

        current_datetime=get_current_datetime()
        start = extract_seconds_current_datetime(activity_scan_seconds)
        #list of actions between activity_scan_seonds amount of seconds ago and current datetime
        actions_list = get_actions_list(start,current_datetime)

        #get interval of time when the patient is sleeping
        #add sleeping interval to current date so the comparison between current time and sleeping interval is correct
        sleep_from = get_current_date_specific_time(scheduler["sleepingFrom"],current_datetime)
        sleep_to = get_current_date_specific_time(scheduler["sleepingTo"],current_datetime)
        print("Sleep from: {}".format(sleep_from))
        print("Sleep to: {}".format(sleep_to))
        #check if it's not time to sleep to see if it's ok to notify
        #if sleep_from<sleep_to
        if len(actions_list)==0 and ((current_datetime<sleep_from or current_datetime>sleep_to) if sleep_from<sleep_to else (current_datetime<sleep_from and current_datetime>sleep_to)):
            global_period_count = 0
            #number of maximum hours between meals
            max_hours_between_meals = scheduler["maxHoursBetweenMeals"] 
            #search for last eat action
            last_action_time_eat = get_last_action_time(1)#1 is id of eat general action
            #request message from notification table
            forgot_eat_notification = get_notification(2)#2 is id of forgot to eat notification
            patient_message_forgot_eat = forgot_eat_notification["patientMessage"]
            caretaker_message_forgot_eat = forgot_eat_notification["caretakerMessage"]
            email_subject = forgot_eat_notification["type"]
            
            #get caretaker info and notification messages
            caretaker = get_caretaker(1)
            caretaker_email = caretaker["email"]

            if last_action_time_eat!="":
                last_action_time_eat = convert_timestamp_to_date(last_action_time_eat)
                #calculate hours between current time and last meal
                hours_interval_eat = get_hours_between(current_datetime,last_action_time_eat)
                #last_notification_eat_time is last notification inserted into notification_tracker about forgot to eat
                last_notification_eat_time = get_last_notification_tracker(2)
                
                if last_notification_eat_time!="":
                    last_notification_eat_time = convert_timestamp_to_date(last_notification_eat_time)
                    minutes_between_notifications = get_minutes_between(current_datetime,last_notification_eat_time)
                    minutes_between_eat_notifications = scheduler["minutesBetweenEatNotifications"] 

                    if hours_interval_eat>max_hours_between_meals  and minutes_between_notifications>minutes_between_eat_notifications:
                        #play notification audio to patient
                        #send email with notification to caretaker
                        send_email(caretaker_email,caretaker_message_forgot_eat,email_subject)
                        
                        print(patient_message_forgot_eat)#"You should eat!"
                        mytext = patient_message_forgot_eat
                        myobj = gTTS(text=mytext, lang=language, slow=False)
                        # Saving the converted audio in a mp3 file named
                        myobj.save("audio_notification.mp3")
                        os.system("omxplayer -o local audio_notification.mp3")

                        #request insert notifiation in db
                        post_into_notification_tracker(current_datetime,forgot_eat_notification)
                else:
                    if hours_interval_eat>max_hours_between_meals:
                        #play notification audio
                        #send email with notification
                        send_email(caretaker_email,caretaker_message_forgot_eat,email_subject)

                        print(patient_message_forgot_eat)
                        mytext = patient_message_forgot_eat
                        myobj = gTTS(text=mytext, lang=language, slow=False)
                        # Saving the converted audio in a mp3 file named
                        myobj.save("audio_notification.mp3")
                        os.system("omxplayer -o local audio_notification.mp3")

                        #request insert notifiation in db
                        post_into_notification_tracker(current_datetime,forgot_eat_notification)
            else:
                #play notification audio
                #send email with notification
                send_email(caretaker_email,caretaker_message_forgot_eat,email_subject)

                print(patient_message_forgot_eat)
                mytext = patient_message_forgot_eat
                myobj = gTTS(text=mytext, lang=language, slow=False)
                # Saving the converted audio in a mp3 file named
                myobj.save("audio_notification.mp3")
                os.system("omxplayer -o local audio_notification.mp3")

                #request insert notifiation in db
                post_into_notification_tracker(current_datetime,forgot_eat_notification)

            # drink verification
            minutes_between_drinks=scheduler["minutesBetweenDrinks"] 
            #request message from notification table
            forgot_drink_notification = get_notification(3)#3 is id of forgot to drink notification
            patient_message_forgot_drink = forgot_drink_notification["patientMessage"]
            caretaker_message_forgot_drink = forgot_drink_notification["caretakerMessage"]
            email_subject = forgot_drink_notification["type"]

            last_action_time_drink = get_last_action_time(2)#2 is id of drink general action

            if last_action_time_drink!="":
                last_action_time_drink = convert_timestamp_to_date(last_action_time_drink)
                #calculate minutes between current time and last drink
                minutes_interval_drink = get_minutes_between(current_datetime,last_action_time_drink)
                #last_notification_drink_time is last notification inserted into notification_tracker about forgot to drink 
                last_notification_drink_time = get_last_notification_tracker(3)
                
                if last_notification_drink_time!="":
                    last_notification_drink_time = convert_timestamp_to_date(last_notification_drink_time)
                    minutes_between_notifications = get_minutes_between(current_datetime,last_notification_drink_time)
                    minutes_between_drink_notifications = scheduler["minutesBetweenDrinkNotifications"] 

                    if minutes_interval_drink>minutes_between_drinks and minutes_between_notifications>minutes_between_drink_notifications:
                        #play notification audio
                        #send email with notification
                        send_email(caretaker_email,caretaker_message_forgot_drink,email_subject)

                        print(patient_message_forgot_drink)#"You should drink!"
                        mytext = patient_message_forgot_drink
                        myobj = gTTS(text=mytext, lang=language, slow=False)
                        # Saving the converted audio in a mp3 file named
                        myobj.save("audio_notification.mp3")
                        os.system("omxplayer -o local audio_notification.mp3")

                        #request insert notifiation in db
                        post_into_notification_tracker(current_datetime,forgot_drink_notification)
                else:
                    if minutes_interval_drink>minutes_between_drinks:
                        #play notification audio
                        #send email with notification
                        send_email(caretaker_email,caretaker_message_forgot_drink,email_subject)

                        print(patient_message_forgot_drink)
                        mytext = patient_message_forgot_drink
                        myobj = gTTS(text=mytext, lang=language, slow=False)
                        # Saving the converted audio in a mp3 file named
                        myobj.save("audio_notification.mp3")
                        os.system("omxplayer -o local audio_notification.mp3")

                        #request insert notifiation in db
                        post_into_notification_tracker(current_datetime,forgot_drink_notification)
            else:
                #play notification audio
                #send email with notification
                send_email(caretaker_email,caretaker_message_forgot_drink,email_subject)

                print(patient_message_forgot_drink)
                
                mytext = patient_message_forgot_drink
                myobj = gTTS(text=mytext, lang=language, slow=False)
                # Saving the converted audio in a mp3 file named
                myobj.save("audio_notification.mp3")
                

                # Playing the converted file
                os.system("omxplayer -o local audio_notification.mp3")

                #os.system("mpg321 'C:\\Users\\catal\\Facultate\\an4\\Semestrul 2\\audio_notification.mp3'")
                
                #request insert notifiation in db
                post_into_notification_tracker(current_datetime,forgot_drink_notification)
        #1 is id of eat general action
        elif 1 in actions_list:
            #start eating time period
            global_period_count = global_period_count + 1
            #check last time before period that is checked now where the action was registered to find last time he ate
            last_action_time_eat = get_last_previous_action(start)
            #request la db pt a scoate nr de minute cat ar trebui sa manance pacientul
            minutes_for_eating = scheduler["eatingMinutes"] 
            #minimum amount of hours from db between eats
            min_hours_between_meals = scheduler["minHoursBetweenMeals"] 
            
            if last_action_time_eat!="":
                last_action_time_eat = convert_timestamp_to_date(last_action_time_eat)
                #calculate hours between current time and last meal
                hours_interval_eat = get_hours_between(current_datetime,last_action_time_eat)
                #if just started eating or eating non stop for too long, more than minutes set for eating, no time between notifications in needed is bad if person is still eating
                if (hours_interval_eat < min_hours_between_meals and global_period_count==1) or (global_period_count>=minutes_for_eating*60/activity_scan_seconds): 
                    #request message from notification table
                    stop_eat_notification = get_notification(1)
                    patient_message_stop_eat = stop_eat_notification["patientMessage"]
                    caretaker_message_stop_eat = stop_eat_notification["caretakerMessage"]
                    email_subject = stop_eat_notification["type"]
                    #play notification audio
                    #send email with notification
                    caretaker = get_caretaker(1)
                    caretaker_email = caretaker["email"]
                    send_email(caretaker_email,caretaker_message_stop_eat,email_subject)

                    print(patient_message_stop_eat)#"You should stop eating!"
                    mytext = patient_message_stop_eat
                    myobj = gTTS(text=mytext, lang=language, slow=False)
                    # Saving the converted audio in a mp3 file named
                    myobj.save("audio_notification.mp3")
                    os.system("omxplayer -o local audio_notification.mp3")

                    #request insert notifiation in db
                    post_into_notification_tracker(current_datetime,stop_eat_notification)
                    

        time.sleep(activity_scan_seconds)