from flask import Flask, jsonify, request
import model.image_classification
import json
import numpy as np

app=Flask(__name__)

@app.route("/classification",methods=['POST'])
def classification():
    if request.method == "POST":
        frame = request.json
        # Deserialization
        print("Decode JSON serialized NumPy array")
        decodedArrays = json.loads(frame)

        finalNumpyArray = np.asarray(decodedArrays["frame"])
        result=image_classification.get_image_classification(finalNumpyArray)
        print(result.__dict__)
        return json.dumps(result.__dict__)


if __name__ == '__main__':
    app.run(debug=True,host='localhost', port=5050)