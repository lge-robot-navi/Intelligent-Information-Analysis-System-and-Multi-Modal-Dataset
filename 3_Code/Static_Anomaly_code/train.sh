#!/bin/bash
#python train.py --dataset "lobby/1Hz" --niter 200 --iheight 12 --iwidth 24 --ngpu 1 --alpha 500 --outf $out --zdim 150 --gpu_ids 3

# training
export PYTHONPATH=.

now=$(date +%Y%m%d-%H:%M)
out="./output/"
out=$out$now
CUDA_VISIBLE_DEVICES=0 python ./script/train.py --phase 'train' --dataset "path-to-dataet" --epochs 1000 --ngpu 1 --outf $out --zdim 150 --batchsize 512 --ngf 64 --size_average


