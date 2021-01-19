import os
import paddlehub as hub

humanSeg = hub.Module(name='deeplabv3p_xception65_humanseg')

print('picking up human ...')

sourceDir = '/Users/neulion/Desktop/images/'
imagePaths = []

fileNames = os.listdir(sourceDir)
for name in fileNames:
    if not name.startswith("."):
        imagePaths.append(sourceDir + name)

for path in imagePaths:
    print('handle image: ' + path)
    result = humanSeg.segmentation(
        paths=[path], visualization=False, output_dir='/Users/neulion/Desktop/humans-temp/')
    data_ = result[0]["data"]
    total = 0
    for subData in data_:
        for num in subData:
            total += num
    print('total: ' + str(total))
    if total > 100000:
        name = path.rsplit(sep='/', maxsplit=1)[1]
        print('is human')
        open('/Users/neulion/Desktop/humans/' +
             name, 'wb').write(open(path, 'rb').read())
    else:
        print('is not human')

    print('')
    os.remove(path)