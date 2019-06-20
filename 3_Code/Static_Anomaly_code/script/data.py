"""
LOAD DATA from file.
"""

##
import os

import numpy as np
import torch
import torchvision.transforms as transforms
from torchvision import datasets
from torchvision.datasets import CIFAR10
from torchvision.datasets import ImageFolder
from torchvision.datasets import MNIST


# this is test by silee
def load_data(opt):
    """ Load Data

    Args:
        opt ([type]): Argument Parser

    Raises:
        IOError: Cannot Load Dataset

    Returns:
        [type]: dataloader
    """

    ##
    # LOAD DATA SET
    if opt.dataroot == '':
        opt.dataroot = './data/{}'.format(opt.dataset)

    if opt.dataset in ['cifar10']:
        splits = ['train', 'test']
        drop_last_batch = {'train': True, 'test': False}
        shuffle = {'train': True, 'test': False}

        transform = transforms.Compose(
            [
                transforms.Scale(opt.isize),
                transforms.ToTensor(),
                transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
            ]
        )

        classes = {
            'plane': 0, 'car': 1, 'bird': 2, 'cat': 3, 'deer': 4,
            'dog': 5, 'frog': 6, 'horse': 7, 'ship': 8, 'truck': 9
        }

        dataset = {}
        dataset['train'] = CIFAR10(root='./data', train=True, download=True, transform=transform)
        dataset['test'] = CIFAR10(root='./data', train=False, download=True, transform=transform)

        dataset['train'].train_data, dataset['train'].train_labels, \
        dataset['test'].test_data, dataset['test'].test_labels = get_cifar_anomaly_dataset(
            trn_img=dataset['train'].train_data,
            trn_lbl=dataset['train'].train_labels,
            tst_img=dataset['test'].test_data,
            tst_lbl=dataset['test'].test_labels,
            abn_cls_idx=classes[opt.anomaly_class]
        )

        dataloader = {x: torch.utils.data.DataLoader(dataset=dataset[x],
                                                     batch_size=opt.batchsize,
                                                     shuffle=shuffle[x],
                                                     num_workers=int(opt.workers),
                                                     drop_last=drop_last_batch[x]) for x in splits}
        return dataloader

    elif opt.dataset in ['mnist']:
        opt.anomaly_class = int(opt.anomaly_class)

        splits = ['train', 'test']
        drop_last_batch = {'train': True, 'test': False}
        shuffle = {'train': True, 'test': True}

        transform = transforms.Compose(
            [
                transforms.Scale(opt.isize),
                transforms.ToTensor(),
                transforms.Normalize((0.1307,), (0.3081,))
            ]
        )

        dataset = {}
        dataset['train'] = MNIST(root='./data', train=True, download=True, transform=transform)
        dataset['test'] = MNIST(root='./data', train=False, download=True, transform=transform)

        dataset['train'].train_data, dataset['train'].train_labels, \
        dataset['test'].test_data, dataset['test'].test_labels = get_mnist_anomaly_dataset(
            trn_img=dataset['train'].train_data,
            trn_lbl=dataset['train'].train_labels,
            tst_img=dataset['test'].test_data,
            tst_lbl=dataset['test'].test_labels,
            abn_cls_idx=opt.anomaly_class
        )

        dataloader = {x: torch.utils.data.DataLoader(dataset=dataset[x],
                                                     batch_size=opt.batchsize,
                                                     shuffle=shuffle[x],
                                                     num_workers=int(opt.workers),
                                                     drop_last=drop_last_batch[x]) for x in splits}
        return dataloader

    else:
        splits = ['train', 'val', 'test']
        drop_last_batch = {'train': True, 'val': False, 'test': False}
        shuffle = {'train': True, 'val': True, 'test': True}

        # transform = transforms.Compose([transforms.Resize((opt.iheight, opt.iwidth)),
        # the PILL Image has 0~1
        # normlizing it would give me -1~+1 values
        transform = transforms.Compose(
            [
                # transforms.Crop((opt.iheight, opt.iwidth*2)),
                # MyCrop(0,0, opt.iheight, opt.iwidth*2),

                # resize or grayscale
                #transforms.Resize((opt.iheight, opt.iwidth)),
                #transforms.Grayscale(num_output_channels=3),

                #transforms.RandomHorizontalFlip(),
                #transforms.RandomApply((
                #    transforms.RandomRotation(15),
                #    transforms.RandomAffine(degrees=0, shear=15),
                #)),
                #transforms.RandomAffine(degrees=15, translate=(0.2, 0.2), shear=15),
                #transforms.RandomAffine(degrees=0, translate=(0.2, 0.2)),
                transforms.ToTensor(), # convert the PIL image in the range [0, 255] to a tensor in the range [0, 1]
                # MyTransformation(chunks=2, splitDim=2, mergeDim=0),
                #transforms.Normalize((0.5), (0.5)) # for gray scale, 1 channel
                transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))  # normalize the tensor by subtract mean and divide it with std
            ])

        #dataset = {x: ImageFolder(os.path.join(opt.dataroot, x), transform) for x in splits}
        dataset = {x: ImageFolderWithPaths(os.path.join(opt.dataroot, x), transform) for x in splits}
        dataloader = {x: torch.utils.data.DataLoader(dataset=dataset[x],
                                                     batch_size=opt.batchsize,
                                                     shuffle=shuffle[x],
                                                     num_workers=int(opt.workers),
                                                     drop_last=drop_last_batch[x]) for x in splits}
        return dataloader


class MyCrop:
    def __init__(self, i, j, h, w):
        self.i = i
        self.j = j
        self.w = w
        self.h = h

    def __call__(self, sample):
        return transforms.functional.crop(sample, self.i, self.j, self.h, self.w)


class MyTransformation:
    def __init__(self, chunks, splitDim, mergeDim):
        self.splitDim = splitDim
        self.mergeDim = mergeDim
        self.chunks = chunks

    def __call__(self, sample):
        chunks = torch.chunk(sample, chunks=self.chunks, dim=self.splitDim)
        # latter = chunks[1]
        # latter[latter==1]=0.0
        combined = torch.cat(chunks, dim=self.mergeDim)
        return combined

class MyTransformation_2:  # (3x(5x14)x48)
    def __call__(self, sample):  # CHW
        chunks1 = torch.chunk(sample, chunks=5, dim=1)

        my_data = []
        for each in chunks1:
            chunks2 = torch.chunk(each, chunks=2, dim=2)
            one = torch.cat(chunks2, dim=0)
            my_data.append(one)
        combined = torch.cat(my_data, dim=0)
        return combined

class ImageFolderWithPaths(datasets.ImageFolder):
    """Custom dataset that includes image file paths. Extends
    torchvision.datasets.ImageFolder
    """

    # override the __getitem__ method. this is the method dataloader calls
    def __getitem__(self, index):
        # this is what ImageFolder normally returns
        original_tuple = super(ImageFolderWithPaths, self).__getitem__(index)
        # the image file path
        path = self.imgs[index][0]
        # make a new tuple that includes original and the path
        tuple_with_path = (original_tuple + (path,))
        return tuple_with_path


    """
    dataset = ImageFolderWithPaths(data_dir)  # our custom dataset
    dataloader = torch.utils.DataLoader(dataset)

    # iterate over data
    for inputs, labels, paths in dataloader:
        # use the above variables freely
        print(inputs, labels, paths)
    """
##

def get_cifar_anomaly_dataset(trn_img, trn_lbl, tst_img, tst_lbl, abn_cls_idx=0):
    """[summary]

    Arguments:
        trn_img {np.array} -- Training images
        trn_lbl {np.array} -- Training labels
        tst_img {np.array} -- Test     images
        tst_lbl {np.array} -- Test     labels

    Keyword Arguments:
        abn_cls_idx {int} -- Anomalous class index (default: {0})

    Returns:
        [np.array] -- New training-test images and labels.
    """
    # Convert train-test labels into numpy array.
    trn_lbl = np.array(trn_lbl)
    tst_lbl = np.array(tst_lbl)

    # --
    # Find idx, img, lbl for abnormal and normal on org dataset.
    nrm_trn_idx = np.where(trn_lbl != abn_cls_idx)[0]
    abn_trn_idx = np.where(trn_lbl == abn_cls_idx)[0]
    nrm_trn_img = trn_img[nrm_trn_idx]  # Normal training images
    abn_trn_img = trn_img[abn_trn_idx]  # Abnormal training images
    nrm_trn_lbl = trn_lbl[nrm_trn_idx]  # Normal training labels
    abn_trn_lbl = trn_lbl[abn_trn_idx]  # Abnormal training labels.

    nrm_tst_idx = np.where(tst_lbl != abn_cls_idx)[0]
    abn_tst_idx = np.where(tst_lbl == abn_cls_idx)[0]
    nrm_tst_img = tst_img[nrm_tst_idx]  # Normal training images
    abn_tst_img = tst_img[abn_tst_idx]  # Abnormal training images.
    nrm_tst_lbl = tst_lbl[nrm_tst_idx]  # Normal training labels
    abn_tst_lbl = tst_lbl[abn_tst_idx]  # Abnormal training labels.

    # --
    # Assign labels to normal (0) and abnormals (1)
    nrm_trn_lbl[:] = 0
    nrm_tst_lbl[:] = 0
    abn_trn_lbl[:] = 1
    abn_tst_lbl[:] = 1

    # Create new anomaly dataset based on the following data structure:
    # - anomaly dataset
    #   . -> train
    #        . -> normal
    #   . -> test
    #        . -> normal
    #        . -> abnormal
    new_trn_img = np.copy(nrm_trn_img)
    new_trn_lbl = np.copy(nrm_trn_lbl)
    new_tst_img = np.concatenate((nrm_tst_img, abn_trn_img, abn_tst_img), axis=0)
    new_tst_lbl = np.concatenate((nrm_tst_lbl, abn_trn_lbl, abn_tst_lbl), axis=0)

    return new_trn_img, new_trn_lbl, new_tst_img, new_tst_lbl


##
def get_mnist_anomaly_dataset(trn_img, trn_lbl, tst_img, tst_lbl, abn_cls_idx=0):
    """[summary]

    Arguments:
        trn_img {np.array} -- Training images
        trn_lbl {np.array} -- Training labels
        tst_img {np.array} -- Test     images
        tst_lbl {np.array} -- Test     labels

    Keyword Arguments:
        abn_cls_idx {int} -- Anomalous class index (default: {0})

    Returns:
        [np.array] -- New training-test images and labels.
    """
    # --
    # Find normal abnormal indexes.
    nrm_trn_idx = torch.from_numpy(np.where(trn_lbl.numpy() != abn_cls_idx)[0])
    abn_trn_idx = torch.from_numpy(np.where(trn_lbl.numpy() == abn_cls_idx)[0])
    nrm_tst_idx = torch.from_numpy(np.where(tst_lbl.numpy() != abn_cls_idx)[0])
    abn_tst_idx = torch.from_numpy(np.where(tst_lbl.numpy() == abn_cls_idx)[0])

    # --
    # Find normal and abnormal images
    nrm_trn_img = trn_img[nrm_trn_idx]  # Normal training images
    abn_trn_img = trn_img[abn_trn_idx]  # Abnormal training images.
    nrm_tst_img = tst_img[nrm_tst_idx]  # Normal training images
    abn_tst_img = tst_img[abn_tst_idx]  # Abnormal training images.

    # --
    # Find normal and abnormal labels.
    nrm_trn_lbl = trn_lbl[nrm_trn_idx]  # Normal training labels
    abn_trn_lbl = trn_lbl[abn_trn_idx]  # Abnormal training labels.
    nrm_tst_lbl = tst_lbl[nrm_tst_idx]  # Normal training labels
    abn_tst_lbl = tst_lbl[abn_tst_idx]  # Abnormal training labels.

    # --
    # Assign labels to normal (0) and abnormals (1)
    nrm_trn_lbl[:] = 0
    nrm_tst_lbl[:] = 0
    abn_trn_lbl[:] = 1
    abn_tst_lbl[:] = 1

    # Create new anomaly dataset based on the following data structure:
    new_trn_img = nrm_trn_img.clone()
    new_trn_lbl = nrm_trn_lbl.clone()
    new_tst_img = torch.cat((nrm_tst_img, abn_trn_img, abn_tst_img), dim=0)
    new_tst_lbl = torch.cat((nrm_tst_lbl, abn_trn_lbl, abn_tst_lbl), dim=0)

    return new_trn_img, new_trn_lbl, new_tst_img, new_tst_lbl
