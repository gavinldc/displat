"# displat" 
使用Eureka作为服务注册发现；
zuul作为公用网关；
虽然微服务化，但是系统还是尽量做到能合不拆，以避免跨系统带来的数据一致性，耦合依赖等问题；
系统内部不再做横向的扩展，没有前后端概念，前台js，app应用全都通过网关以rest的方式调用服务；
websocket的方式待改进；
内部长连接是用netty+protoBuf实现的rpc，netty的高可用依然使用Eureka做服务发现，客户端通过ribbon轮询的方式实现负责均衡；


