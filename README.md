# Remote-Sensing-of-Crop-Pathology-through-Computer-Vision
Plants are subject to a variety of disorders caused by diseases or environmental factors.
The ability to detect and diagnose these disorders remotely provides advantages for agriculture.
The goal was to develop software in Java that can remotely identify and differentiate between
various crop pathologies. To accomplish this, images from webcam feeds were converted to an
array of pixels and maximum and minimum thresholds for the hue, saturation, and value (HSV)
of the infected regions of plants were determined. These values were used to create a mask of
areas in the image that were within the correct color range which was then further processed to
reduce noise.
