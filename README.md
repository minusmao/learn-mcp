# learn-mcp
学习《MCP中文文档》（文档地址：<https://mcp-docs.cn/>）

## MCP通用架构
```mermaid
flowchart LR
    subgraph local["你的计算机"]
        subgraph host["MCP Host (AI Application)"]
            clientA["MCP Client A"] 
            clientB["MCP Client B"]
            clientC["MCP Client C"]
        end

        serverA["MCP Server A"]
        clientA -- "MCP 协议（Stdio/SSE）" --> serverA

        serverC["MCP Server C"]
        clientC -- "MCP 协议（Stdio/SSE）" --> serverC
    end

    subgraph internet["互联网"]
        serverB["MCP Server B"]
        clientB -- "MCP 协议（SSE）" --> serverB
        webC["Web Server C"]
        serverC -- "Web APIs" --> webC
    end
```
* MCP Host: 通常为可以调用AI大模型的AI应用（如Claude Desktop），并内置有MCP客户端（MCP Client）
* MCP Client: 维护与MCP服务器（MCP Server）一对一连接的协议客户端
* MCP Server: 提供MCP服务，通过标准的MCP协议提供特定能力，传输方式包括两种（Stdio本地进程、SSE网络服务）
* Web Server: 提供Web服务，并提供一定功能的API（如高德的行政区划、天气信息查询）给MCP服务器（MCP Server）调用

## MCP服务器（传输方式：Stdio本地进程）
使用现有的AI应用（Claude Desktop）测试，需要修改配置文件（MacOS）：`/Users/用户名/Library/Application Support/Claude/claude_desktop_config.json`
```json
{
  "mcpServers": {
    "spring-ai-mcp-weather": {
      "command": "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin/java",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "项目绝对路径/learn-mcp/mcp-server/target/mcp-server-0.0.1-SNAPSHOT.jar"
      ],
      "env": {
        "AMAP_API_KEY": "高德API密钥"
      }
    }
  }
}
```

此MCP服务器通过调用高德API的方式查询行政区划、天气信息，所以需要前往[高德开放平台](https://lbs.amap.com/)申请高德API密钥

如果启动Claude Desktop报错，可以通过如下命令查看实时日志：
```bash
tail -n 20 -F ~/Library/Logs/Claude/mcp*.log
```

## MCP客户端（传输方式：Stdio本地进程）
调用已经写好的MCP服务器，需要修改配置文件`./mcp-client/src/main/resources/mcp-servers-config.json`

此MCP客户端测试，需要用到AI大模型，由于文档中的[anthropic平台](https://www.anthropic.com/)付费比较麻烦，所以这里使用[硅基流动平台](https://cloud.siliconflow.cn/)或[ollama平台](https://ollama.com/)（本地模型）

### `ollama`的使用：
* 运行模型（如果没有，会自动下载模型）
```bash
ollama run qwen3:1.7b
```
* 停止模型
```bash
ollama stop qwen3:1.7b
```

注：在`ollama`官网的`Models`菜单中会列出有哪些模型可用，其中有`tools`标签的才支持MCP调用
