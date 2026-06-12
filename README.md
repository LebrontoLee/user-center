# User Center

用户中心 — 前后端一体化项目。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 2.6.13 + MyBatis-Plus 3.5 + Java 8 + Maven |
| 前端 | Ant Design Pro 5 + Umi 3 + React + TypeScript |

## 项目结构

```
user-center/
├── src/main/java/com/lee/usercenterbackend/   # 后端 Java 源码
├── src/main/resources/                         # 后端配置文件 & Mapper XML
├── src/test/                                   # 后端单元测试
├── pom.xml                                     # Maven 配置
└── user-center-frontend/                       # 前端项目
    ├── config/                                 # Umi 配置
    ├── src/pages/                              # 页面组件
    ├── src/services/                           # API 请求
    ├── mock/                                   # Mock 数据
    └── package.json                            # 前端依赖
```

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- Node.js 16+ / npm 或 yarn

### 后端

```bash
# 启动后端服务（默认 8080 端口）
mvn spring-boot:run
```

### 前端

```bash
cd user-center-frontend

# 安装依赖
npm install

# 启动开发服务器（默认 8000 端口）
npm start
```

## 构建部署

```bash
# 后端打包
mvn clean package -DskipTests

# 前端打包
cd user-center-frontend && npm run build
```
