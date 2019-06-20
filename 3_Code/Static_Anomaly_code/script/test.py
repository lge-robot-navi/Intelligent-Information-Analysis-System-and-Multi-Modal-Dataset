"""
Test GANOMALY

. Example: Run the following command from the terminal.
    run test.py                             \
        --model ganomaly                        \
        --dataset UCSD_Anomaly_Dataset/UCSDped1 \
        --batchsize 32                          \
        --isize 256                         \
        --nz 512                                \
        --ngf 64                               \
        --ndf 64
"""

##
# LIBRARIES
from __future__ import print_function

from script.data import load_data
from script.model_4_human import AAE_basic
from script.options import Options


##
def main():
    """ Training
    """

    ##
    # ARGUMENTS
    opt = Options().parse()

    ##
    # LOAD DATA
    dataloader = load_data(opt)

    ##
    # LOAD MODEL
    model = AAE_basic(opt, dataloader)

    ##
    # TRAIN MODEL
    model.test()


if __name__ == '__main__':
    main()
