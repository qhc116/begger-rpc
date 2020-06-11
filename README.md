# begger-rpc
仅仅是学习了netty之后的一个小demo后期肯定会重构，预计学习DUBBO源码之后参考重构


# 具体重构方向
1. 反射调用改为从容器中的类调用
2. 实现服务拆分、并且可以同时作为provider和comsumer
3. 使用protobuf代替json作为序列化工具
4. 加入monitor链路监控
5. 限流、熔断、统计
