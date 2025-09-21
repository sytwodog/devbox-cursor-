# 员工管理系统

这是一个基于Spring Boot的员工管理系统，提供了用户认证、员工信息管理等功能。

## 技术栈

- Spring Boot
- Spring Security
- MyBatis
- MySQL
- JWT

## 主要功能

- 用户登录认证
- 员工信息管理
- 部门管理
- 文件上传

## 快速开始

1. 克隆项目
```bash
git clone https://github.com/sytwodog/employee-management.git
```

2. 配置数据库
- 复制 `src/main/resources/application.properties.example` 为 `application.properties`
- 修改数据库连接信息

3. 运行项目
```bash
./mvnw spring-boot:run
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

## 注意事项

- 首次使用请修改 `application.properties` 中的数据库配置
- 默认服务端口为 8080
- 文件上传大小限制为 10MB