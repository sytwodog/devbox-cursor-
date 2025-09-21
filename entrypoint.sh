#!/bin/bash

# 检查是否为生产环境
if [ "$1" = "production" ]; then
    echo "Building for production..."
    npm run build
    npm run preview
else
    echo "Starting development server..."
    # 安装依赖
    npm install
    # 启动开发服务器
    npm run dev
fi