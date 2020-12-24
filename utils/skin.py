import os

import paddlehub as hub

humanSeg = hub.Module(name='deeplabv3p_xception65_humanseg')

print('picking up human ...')

sourceDir = '/Users/neulion/Desktop/flatqq-images/'
imagePaths = []

fileNames = os.listdir(sourceDir)
for name in fileNames:
    if not name.startswith("."):
        imagePaths.append(sourceDir + name)

for path in imagePaths:
    print('handle image: ' + path)
    result = humanSeg.segmentation(paths=[path], visualization=True, output_dir='/Users/neulion/Desktop/flatqq-human/')
    data_ = result[0]["data"]
    total = 0
    for subData in data_:
        for num in subData:
            total += num
    if total < 10000:
        os.remove(result[0]["save_path"])
    print(result[0]["save_path"])
    print(total)
