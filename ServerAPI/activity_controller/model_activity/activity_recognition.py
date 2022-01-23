import tensorflow as tf
tf.get_logger().setLevel('ERROR')           # Suppress TensorFlow logging (2)
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'    # Suppress TensorFlow logging (1)
import numpy as np
import cv2
from model_activity.activity_recognition_class import ActivityRecognition

# classes and variables for activity recognition in video
# load the contents of the class labels file, then define the sample
# duration (i.e., # of frames for classification) and sample size
# (i.e., the spatial dimensions of the frame)
CLASSES = open("C:\\Users\\catal\\Facultate\\an4\\licenta\\ActivityRecognition\\action_recognition_kinetics.txt").read().strip().split("\n")
SAMPLE_SIZE = 112

# load the human activity recognition model
print("[INFO] loading human activity recognition model...")
net = cv2.dnn.readNet("C:\\Users\\catal\\Facultate\\an4\\licenta\\ActivityRecognition\\resnet-34_kinetics.onnx")

def get_activity_recognition(frames):
    #construct blob for video classification
    # now that our frames array is filled we can construct our blob
    blob = cv2.dnn.blobFromImages(np.array(frames, dtype='uint8'), 1.0,
        (SAMPLE_SIZE, SAMPLE_SIZE), (114.7748, 107.7354, 99.4750),
        swapRB=True, crop=True)
    blob = np.transpose(blob, (1, 0, 2, 3))
    blob = np.expand_dims(blob, axis=0)

    # pass the blob through the network to obtain our human activity recognition predictions
    net.setInput(blob)
    outputs = net.forward()
    label = CLASSES[np.argmax(outputs)] 

    #calculate score out of outputs values knowing that sum of all predictions for all classes is 0
    min_value = min(outputs[0])
    abs_value = abs(min_value)
    outputs[0] = list(np.asarray(outputs[0]) + abs_value)
    max_value = max(outputs[0])
    sum_of_scores = sum(outputs[0])
    score = int(max_value/sum_of_scores*100*100)
    if score > 100: score = 100
    print("Score is {}%".format(score))

    post_process = ActivityRecognition(label, score)
    return post_process