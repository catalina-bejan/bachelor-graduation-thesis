#python process_server.py 
#python process_server.py  -v "C:\Users\catal\Facultate\an4\licenta\Object Detection\ObjDetDrive\TensorFlow\scripts\video11.mp4" 
#python process_server.py -s true
#python serverProcessing.py -s true
import tensorflow as tf
tf.get_logger().setLevel('ERROR')           # Suppress TensorFlow logging (2)
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'    # Suppress TensorFlow logging (1)
from object_detection.utils import config_util
from object_detection.utils import label_map_util
from object_detection.utils import visualization_utils as viz_utils
from object_detection.builders import model_builder
import pathlib
import time
import cv2
import numpy as np
import six
from datetime import datetime,timezone,timedelta
import argparse

# import the necessary packages
from imutils import build_montages
import numpy as np
import imagezmq
import argparse
import imutils
import cv2
import websockets
import base64
import asyncio
from threading import Thread
import object_detection_process
import activity_recognition
import requests
import json
from json import JSONEncoder
import imageio
import io

# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-v", "--video", type=str, default="",
	help="optional path to video file")
ap.add_argument("-s", "--streaming", type=str, default="",
	help="optional stream video from raspberry pi camera")

args = vars(ap.parse_args())
#frames for video activity recognition
SAMPLE_DURATION = 16

class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return JSONEncoder.default(self, obj)

def get_current_datetime():
    # datetime object containing current date and time
    a_datetime = datetime.now()
    formatted_datetime = a_datetime.isoformat()
    return formatted_datetime

def extract_seconds_current_datetime(seconds):
    # datetime object containing current date and time
    a_datetime = datetime.now()- timedelta(seconds=seconds)
    formatted_datetime = a_datetime.isoformat()
    return formatted_datetime


def video_file():
    global frame_number
    global cap
    #take a frame only once 50 frames for video file:
    frame_number += 70
    cap.set(cv2.CAP_PROP_POS_FRAMES, frame_number)
    ret, frame = cap.read()
    return frame

max_boxes_to_draw=6
min_score_thresh=.7

# Number of connected users to websockets
connected = set()

#Handling connected clients to websockets
async def handler(websocket, path):
    # add new client when connected to websocket
    connected.add(websocket)
    
    try:
        while True:
            # send processed image data to websocket
            await websocket.send(data)
            #await asyncio.sleep(0.1)
    finally:
        # remove client if disconnected
        connected.remove(websocket)
        
#Handling connected clients to websockets
async def handler2(websocket, path):
    # add new client when connected to websocket
    connected.add(websocket)
    
    try:
        while True:
            #print('sending data')
            # send processed image data to websocket
            await websocket.send(data2)
            #await asyncio.sleep(0.1)
    finally:
        # remove client if disconnected
        connected.remove(websocket)

def init_server():
    print('starting server...')

    asyncio.set_event_loop(loop)
    start_server = websockets.serve(handler, "192.168.0.106", 5678)
    loop.run_until_complete(start_server)
    start_server2 = websockets.serve(handler2, "192.168.0.106", 5679)
    loop.run_until_complete(start_server2)
    loop.run_forever()

if __name__ == "__main__":
    # initialize image data with default no signal image
    data = "default image data for activity recognition"
    data2 = "default image data for object detection"

    # get list of all searched actions from database using api request
    searched_activity_json = requests.get('http://127.0.0.1:8080/api/searchedActivities/activityname/all')
    searched_activity_list = searched_activity_json.json()

    # get list of searched actions sugesting eating from database using api request
    eat = {'type':'eat'}
    searched_activity_eat_json = requests.get('http://127.0.0.1:8080/api/searchedActivities/activityname', params=eat)
    searched_activity_eat_list = searched_activity_eat_json.json()

    # get list of searched actions sugesting drinking from database using api request
    drink = {'type':'drink'}
    searched_activity_drink_json = requests.get('http://127.0.0.1:8080/api/searchedActivities/activityname', params=drink)
    searched_activity_drink_list = searched_activity_drink_json.json()
    
    # create separate thread for websocket
    loop = asyncio.new_event_loop()
    t = Thread(target=init_server, daemon=True)
    t.start()

    # width = 400
    # height = 300
    # out = cv2.VideoWriter('C:\\Users\\catal\\Facultate\\an4\\Semestrul 2\\video.mp4',cv2.VideoWriter_fourcc(*'mp4v'), 10, (width, height))
    # nr=1

    #initialize first frame read from video file
    frame_number = 1
    if args["streaming"]:
        #prediction on image from raspberry pi camera
        print("streaming from socket")
        # initialize the ImageHub object
        imageHub = imagezmq.ImageHub()
        time_between_frames = 0.1
    else:
        #prediction on video file or lp webcam
        cap = cv2.VideoCapture(args["video"] if args["video"] else 0)
        time_between_frames = 0.1

    while True:
        print('main loop: --- connections: ' + str(len(connected)))
        # initialize the batch of frames that will be passed through the activity recognition model
        frames = []
        frame_number += 1
        # loop over the number of required sample frames
        for i in range(0, SAMPLE_DURATION):
            if args["streaming"]:
                # receive RPi name and frame from the RPi and acknowledge the receipt
                (rpiName, frame) = imageHub.recv_image()
                imageHub.send_reply(b'OK')
                time.sleep(0.1)
            else:
                #frame = video_file()
                #take a frame only once 3 frames for video file:
                cap.set(cv2.CAP_PROP_POS_FRAMES, frame_number)
                ret, frame = cap.read()
                frame_number += 3
                time.sleep(0.1)

            #resize frame and add it to the frames list
            frame=imutils.resize(frame, width=400)
            frames.append(frame)

        # height,width,layers = frame.shape
        # print("height={} & width={}".format(height,width))
        #do obj det and image classification only on last frame from batch for video classification
        post_process_object = object_detection_process.get_objdet_image_detections(frame)
        image_np_with_detections = post_process_object.image
        detections = post_process_object.detections

        print(object_detection_process.get_classes_name_and_scores(
		detections['detection_boxes'],
		detections['detection_classes'],
		detections['detection_scores'],
		max_boxes_to_draw,
		min_score_thresh))

        #Object Detection request
        # numpyData = {"frame": frame}
        # encodedNumpyData = json.dumps(numpyData, cls=NumpyArrayEncoder)  # use dump() to write array into file
        # headers={'Content-type':'application/json', 'Accept':'application/json'}
        # post_process_object = requests.post("http://localhost:9090/object", json=encodedNumpyData,headers=headers)
        # post_process_object = post_process_object.json()
        # image_np_with_detections = np.asarray(post_process_object["frame"])

        #Image classification request
        numpyData = {"frame": frame}
        encodedNumpyData = json.dumps(numpyData, cls=NumpyArrayEncoder)  # use dump() to write array into file
        headers={'Content-type':'application/json', 'Accept':'application/json'}
        post_process_classification = requests.post("http://localhost:5050/classification", json=encodedNumpyData,headers=headers)
        post_process_classification = post_process_classification.json()
        label_classification = post_process_classification["label"]
        score_classification = post_process_classification["binary_score"]
        print(label_classification)
        image_np_with_detections = cv2.putText(image_np_with_detections, "{}: {}".format(label_classification,score_classification), (10, 25), cv2.FONT_HERSHEY_SIMPLEX,
                0.8, (255, 255, 255), 2)
    

        #Activity recognition request
        # numpyFrames = {"frames": frames}
        # encodedNumpyFrames = json.dumps(numpyFrames, cls=NumpyArrayEncoder)  # use dump() to write array into file
        # headers={'Content-type':'application/json', 'Accept':'application/json'}
        # post_process_activity = requests.post("http://localhost:9090/activity", json=encodedNumpyFrames,headers=headers)
        # post_process_activity = post_process_activity.json()
        # label_activity = post_process_activity["label"]
        # score_activity = post_process_activity["score"]
        # print(label_activity)

        post_process_activity = activity_recognition.get_activity_recognition(frames)
        label_activity = post_process_activity.label
        score_activity = post_process_activity.score
        print("activity recognition label is ", label_activity)

        # loop over video frames
        rgb_frames=[]
        for frame in frames:
            # draw the predicted activity on the frame only if the label is in the searched list
            if label_activity in searched_activity_list:
                query = {"activity": label_activity}
                general_action = requests.get('http://localhost:8080/api/generalActions', params=query)
                general_action = general_action.text
                frame = cv2.rectangle(frame, (0, 0), (400, 30), (0, 0, 0), -1) #scorul ar putea sa nu mai fie acelasi pt ca acum afisez scorul unei avcituni generale si nu a actiunii respective
                frame = cv2.putText(frame, "{}: {}%".format(general_action,score_activity), (10, 25), cv2.FONT_HERSHEY_SIMPLEX,
                    0.8, (255, 255, 255), 2)

            #do obj det and image classification only on last frame from batch for video classification
            image_np = np.array(frame)
            rgb_frames.append(cv2.cvtColor(frame, cv2.COLOR_BGR2RGB))
            #send image if any client is connected to websocket
            if len(connected) != 0:
                #print('sending custom data...')
                #cv2.imshow('object detection', cv2.resize(image_np_with_detections, (400,300)))
                #encode image for websocket transfer
                ret, buffer = cv2.imencode('.jpg', image_np)#image_np_with_detections
                jpg_as_text = base64.b64encode(buffer)
                data = str(jpg_as_text)
                ret, buffer = cv2.imencode('.jpg', image_np_with_detections)#image_np_with_detections
                jpg_as_text = base64.b64encode(buffer)
                data2 = str(jpg_as_text)
            time.sleep(time_between_frames)
        #creating gif in memory out of frames and then convert it to base64
        #rgb_frames = cv2.cvtColor(frames, cv2.COLOR_BGR2RGB)

        if label_activity in searched_activity_list:
            gif_file = io.BytesIO()
            imageio.mimsave(gif_file, rgb_frames, 'GIF')
            detect_base64 = 'data:image/gif;base64,{}'.format(base64.b64encode(gif_file.getvalue()).decode())
            # get list of searched actions sugesting eating from database using api request
            query = {'name':label_activity}
            searched_activity_idJson = requests.get('http://127.0.0.1:8080/api/searchedActivities/findid', params=query)
            searched_activity_id = searched_activity_idJson.json()
            #insert object in videoTable
            current_datetime = get_current_datetime()
            headers={'Content-type':'application/json', 'Accept':'application/json'}
            if label_activity in searched_activity_eat_list:
                video_json_object = {"footage": detect_base64, "timestamp": current_datetime, "searchedActivity": {"id": searched_activity_id,"activityName": label_activity,"generalAction": {"id": 1,"type": general_action}},"score": score_activity}
                response = requests.post('http://127.0.0.1:8080/api/videos', json = video_json_object, headers=headers)
            elif label_activity in searched_activity_drink_list:
                video_json_object = {"footage": detect_base64, "timestamp": current_datetime, "searchedActivity": {"id": searched_activity_id,"activityName": label_activity,"generalAction": {"id": 2,"type": general_action}},"score": score_activity}
                response = requests.post('http://127.0.0.1:8080/api/videos', json = video_json_object, headers=headers)
                
            #save base64 gif into txt file:
            # with open('C:\\Users\\catal\\Facultate\\an4\\Semestrul 2\\testText.txt', 'w') as f:
            #     f.write(detect_base64)
            
        #imageio.mimwrite('C:\\Users\\catal\\Facultate\\an4\\Semestrul 2\\animated3.gif',frames)
        #     if nr <= 16:
        #         out.write(frame)
        #         nr=nr+1
        # if nr == 17:
        #     out.release()
        if cv2.waitKey(1) & 0xFF == ord('q'):
            #cap.release()
            break
        #time.sleep(1)

# do a bit of cleanup
cv2.destroyAllWindows()