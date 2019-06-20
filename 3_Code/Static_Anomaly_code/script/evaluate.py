""" Evaluate ROC

Returns:
    auc, eer: Area under the curve, Equal Error Rate
"""

# pylint: disable=C0103,C0301

##
# LIBRARIES
from __future__ import print_function

import os

import matplotlib.pyplot as plt
from matplotlib import rc
from scipy.interpolate import interp1d
from scipy.optimize import brentq
from sklearn.metrics import roc_curve, auc, confusion_matrix

rc('font', **{'family': 'serif', 'serif': ['Computer Modern']})


# rc('text', usetex=True)

##
def roc(labels, scores, saveto=None):
    """Compute ROC curve and ROC area for each class"""
    fpr = dict()
    tpr = dict()
    roc_auc = dict()

    # True/False Positive Rates.
    fpr, tpr, thresholds = roc_curve(labels, scores, pos_label=1)
    roc_auc = auc(fpr, tpr)

    # Equal Error Rate
    eer = brentq(lambda x: 1. - x - interp1d(fpr, tpr)(x), 0., 1.)

    if saveto:
        # plt.figure()
        lw = 2
        label = '(AUC = %0.4f, EER = %0.4f)' % (roc_auc, eer)
        plt.plot(fpr, tpr, color='darkorange', lw=lw, label=label)
        # plt.show()
        plt.plot([eer], [1 - eer], marker='o', markersize=5, color="navy")
        plt.plot([0, 1], [1, 0], color='navy', lw=1, linestyle=':')
        plt.xlim([0.0, 1.0])
        plt.ylim([0.0, 1.05])
        plt.xlabel('False Positive Rate')
        plt.ylabel('True Positive Rate')
        plt.title('Receiver operating characteristic')
        plt.legend(loc="lower right")

        # ax2 = plt.gca().twinx()
        # ax2.plot(fpr, thresholds, markeredgecolor='r', linestyle='dashed', color='r')
        # ax2.set_ylabel('Threshold', color='r')
        # ax2.set_ylim([thresholds[0], thresholds[-1]])
        # ax2.set_xlim([fpr[0], fpr[-1]])
        plt.savefig(os.path.join(saveto, "ROC.pdf"))
        plt.close()

    thr = thresholds[-(tpr[tpr >= 0.85].size - 1)]
    return roc_auc, eer, thr


def calculate_confusion_matrix(labels, prediction, threshold):
    """Compute ROC curve and ROC area for each class"""

    p = prediction.cpu().numpy()
    l = labels.cpu().numpy()
    # prediction[prediction >= threshold] = 1.0
    # prediction[prediction != 1.0] = 0.0

    p[p >= threshold] = 1.0
    p[p != 1.0] = 0.0
    cm = confusion_matrix(l, p)

    return cm
