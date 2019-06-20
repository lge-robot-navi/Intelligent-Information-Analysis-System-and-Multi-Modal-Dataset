#!/bin/bash
#now=$(date +%Y%m%d-%H:%M)
#out="./output/"
#out=$out$now
#python train.py --dataset "lobby/1Hz" --niter 200 --iheight 12 --iwidth 24 --ngpu 1 --alpha 500 --outf $out --zdim 150 --gpu_ids 3

# TODO: train.sh 와 test.sh 를 합쳐서 run.sh로 만들기?
export PYTHONPATH=.
python ./script/test.py --phase 'test' --dataset "resize/" --batchsize 512 --epochs 50 --iheight 64 --iwidth 64 --ngpu 1 --alpha 50 --load_weights --outf "path-to-trained-output" --zdim 150 --extralayers 0
