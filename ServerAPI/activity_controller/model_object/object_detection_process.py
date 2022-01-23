import tensorflow as tf
tf.get_logger().setLevel('ERROR')           # Suppress TensorFlow logging (2)
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'    # Suppress TensorFlow logging (1)
from object_detection.utils import label_map_util
from object_detection.utils import visualization_utils as viz_utils
import time
import numpy as np
import six
from imutils import build_montages
from model_object.object_detection_class import ObjectDetection

PATH_TO_LABELS = "C:\\Users\\catal\\Facultate\\an4\\licenta\\Object Detection\\ObjDetDrive\\TensorFlow\\scripts\\ObjectDetectionModels\\mscoco_label_map.pbtxt"
PATH_TO_MODEL = "C:\\Users\\catal\\Facultate\\an4\\licenta\\Object Detection\\ObjDetDrive\\TensorFlow\\scripts\\ObjectDetectionModels\\faster_rcnn_resnet50_v1_640x640_coco17_tpu-8\\saved_model"

print('Loading model...', end='')
start_time = time.time()

# Load saved model and build the detection function
detect_fn = tf.saved_model.load(PATH_TO_MODEL)

end_time = time.time()
elapsed_time = end_time - start_time
print('Done! Took {} seconds'.format(elapsed_time))

#Load label map data for plotting
category_index = label_map_util.create_category_index_from_labelmap(PATH_TO_LABELS,
                                                                    use_display_name=True)

max_boxes_to_draw=6
min_score_thresh=.7

def get_classes_name_and_scores(
        boxes,
        classes,
        scores,
        max_boxes_to_draw,
        min_score_thresh): 
    list = ["person","bottle","wine glass","cup","fork","knife","spoon","bowl","banana","apple","sandwich","orange","broccoli","carrot","hot dog","pizza","donut","cake","dining table"]
    display_str = {}
    my_obj_det = []
    if not max_boxes_to_draw:
        max_boxes_to_draw = boxes.shape[0]
    for i in range(min(max_boxes_to_draw, boxes.shape[0])):
        if scores is None or scores[i] > min_score_thresh:
            if classes[i] in six.viewkeys(category_index) and category_index[classes[i]]['name'] in list:
                display_str['name%d' %i] = category_index[classes[i]]['name']
                display_str['score%d' %i] = '{}%'.format(int(100 * scores[i]))
                display_str['box%d' %i] = boxes[i]
                #my_obj_det.append(display_str)
    return display_str  

def create_detections(detections):		
    num_detections = int(detections.pop('num_detections'))
    detections = {key: value[0, :num_detections].numpy()
						for key, value in detections.items()}
    detections['num_detections'] = num_detections
		
    #detection_classes should be ints.
    detections['detection_classes'] = detections['detection_classes'].astype(np.int64)
    return detections

def image_to_tensor(image_np):
    input_tensor = tf.convert_to_tensor(image_np)
    input_tensor = input_tensor[tf.newaxis, ...]
    return input_tensor

def get_objdet_image_detections(frame):
    image_np = frame
    image_np = image_np.astype(np.uint8)
    input_tensor = image_to_tensor(image_np)
    detections = create_detections(detect_fn(input_tensor))
    image_np_with_detections = image_np.copy()
    max_boxes_to_draw=6
    min_score_thresh=.7

    viz_utils.visualize_boxes_and_labels_on_image_array(
                    image_np_with_detections,
                    detections['detection_boxes'],
                    detections['detection_classes'],
                    detections['detection_scores'],
                    category_index,
                    use_normalized_coordinates=True,
                    max_boxes_to_draw=6,
                    min_score_thresh=.7,
                    agnostic_mode=False)

    post_process = ObjectDetection(image_np_with_detections,detections)
    return post_process

def get_objdet_detections_only_image(frame):
    image_np = frame
    image_np = image_np.astype(np.uint8)
    input_tensor = image_to_tensor(image_np)
    detections = create_detections(detect_fn(input_tensor))
    image_np_with_detections = image_np.copy()
    max_boxes_to_draw=6
    min_score_thresh=.7

    viz_utils.visualize_boxes_and_labels_on_image_array(
                    image_np_with_detections,
                    detections['detection_boxes'],
                    detections['detection_classes'],
                    detections['detection_scores'],
                    category_index,
                    use_normalized_coordinates=True,
                    max_boxes_to_draw=6,
                    min_score_thresh=.7,
                    agnostic_mode=False)

    return image_np_with_detections

def get_category_index():
    return category_index