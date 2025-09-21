# 智学云帆-教学管理系统

这是一个完整的教学管理系统，包含前后端代码。后端基于Spring Boot开发，前端使用Vue.js 3构建。

## 技术栈

### 后端技术栈
- Spring Boot
- Spring Security
- MyBatis
- MySQL
- JWT

### 前端技术栈
- Vue.js 3
- Vite
- Element Plus
- Vue Router
- Axios

## 主要功能

- 用户登录认证
- 员工信息管理
- 部门管理
- 文件上传
- 响应式布局
- 权限控制

## 快速开始

### 后端启动
1. 配置数据库
   - 复制 `src/main/resources/application.properties.example` 为 `application.properties`
   - 修改数据库连接信息

2. 运行后端
```bash
./mvnw spring-boot:run
```

### 前端启动
1. 安装依赖
```bash
npm install
```

2. 开发模式运行
```bash
npm run dev
```

3. 生产环境构建
```bash
npm run build
```

## 接口文档

### 登录接口
- POST `/login`
- 请求体：
```json
{
    "username": "用户名",
    "password": "密码"
}
```

### 员工管理接口
- GET `/emps` - 获取员工列表
- POST `/emps` - 添加员工
- PUT `/emps` - 更新员工信息
- DELETE `/emps/{id}` - 删除员工

## 系统配置

### 后端配置
- 默认服务端口：8080
- 文件上传大小限制：10MB

### 前端配置
- 开发服务器端口：3000
- 接口代理配置：见 `vite.config.js`

## 注意事项

- 首次使用请修改后端 `application.properties` 中的数据库配置
- 确保后端服务器正常运行后再启动前端服务
- 文件上传功能需要正确配置存储路径