# begger-rpc
仅仅是学习了netty之后的一个小demo后期肯定会重构，预计学习DUBBO源码之后参考重构
当前缺陷：
1. 服务没有拆分
2. fastjson序列化丧失了基于TCP的优势
3. 基于反射调用，比较消耗性能
4. 新增节点、删除节点的时候是重新遍历，然后新建 channel 比较消耗资源


# 具体重构方向
1. 反射调用改为从容器中调用
4. 优化链路管理
2. 实现服务拆分、并且可以同时作为 provider 和 comsumer
3. 使用protobuf作为序列化工具
4. 加入monitor链路监控、统计
5. 限流、熔断
