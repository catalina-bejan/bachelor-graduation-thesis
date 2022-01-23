import cv2
import numpy as np
from keras.models import load_model
from image_classification_class import ImageClassification

export_path_keras = "C:\\Users\\catal\\Facultate\\an4\\licenta\\modele antrenate\\modelBinaryClassificationFoodOrNotfood-VGG16.h5"
model = load_model(export_path_keras)
classtolabel = {'0':'food','1':'notfood'}

def get_image_classification(frame):
    processed_frame = cv2.resize(np.array(frame, dtype='uint8'), (224,224))
    processed_frame = np.array(processed_frame).astype('float32')/255
    processed_frame = np.expand_dims(processed_frame, axis=0)
    result = model.predict(processed_frame)
    y_pred = (result[0][0].round()).astype(int)
    label = classtolabel[str(y_pred)]
    binary_score = "{:.2f}".format(result[0][0])
    
    post_process = ImageClassification(label, binary_score)
    return post_process