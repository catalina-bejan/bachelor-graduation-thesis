from flask import Flask, jsonify, request
import activity_recognition
import object_detection_process
import json
from json import JSONEncoder
import numpy as np

app=Flask(__name__)

class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return JSONEncoder.default(self, obj)

@app.route("/activity",methods=['POST'])
def activity():
    if request.method == "POST":
        frames = request.json
        # Deserialization
        print("Decode JSON serialized NumPy array")
        data  = json.loads(frames)
        finalNumpyArray = np.asarray(data["frames"])
        result=activity_recognition.get_activity_recognition(finalNumpyArray)
        # print(result.__dict__)
        return json.dumps(result.__dict__)

@app.route("/object",methods=['POST'])
def object():
    if request.method == "POST":
        frame = request.json
        # Deserialization
        print("Decode JSON serialized NumPy array")
        data  = json.loads(frame)
        finalNumpyArray = np.asarray(data["frame"])
        result=object_detection_process.get_objdet_detections_only_image(finalNumpyArray)
        post_frame = result
        numpyData = {"frame": post_frame}
        encodedNumpyData = json.dumps(numpyData, cls=NumpyArrayEncoder)
        return encodedNumpyData


if __name__ == '__main__':
    app.run(debug=True,host='localhost', port=9090)