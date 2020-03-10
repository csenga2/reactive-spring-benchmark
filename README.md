# reactive-spring-benchmark
# Intro
The goal of this benchmark is to showcase the possible performance gain of using **Spring WebFlux** over **Spring Web**.  
The implemented services have a HTTP POST endpoint which triggers an entity save(sync/async) then the persisted entity is sent(async in both services) to a kafka topic. 

# Run
1. `./mvnw clean install` for both project
2. `docker-compose --compatibility up -d --build`
3. download [vegeta](https://github.com/tsenart/vegeta) and run the given vegeta commands

# Test environment
i7-8750H  
Windows 10 Pro x64  
Docker 19.03.5 (6 thread/6GB)  

# Results with _--max-workers=500_

## Spring Web

best result:
```
$ echo "POST http://localhost:9002" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=500 | tee results.bin | ./vegeta.exe report  
Requests      [total, rate, throughput]         8833, 883.22, 838.65  
Duration      [total, attack, wait]             10.532s, 10.001s, 531.458ms  
Latencies     [min, mean, 50, 90, 95, 99, max]  3.967ms, 582.039ms, 499.162ms, 1.003s, 1.245s, 1.722s, 3.211s  
Bytes In      [total, mean]                     980463, 111.00  
Bytes Out     [total, mean]                     0, 0.00  
Success       [ratio]                           100.00%  
Status Codes  [code:count]                      200:8833  
Error Set:  
```

## Spring WebFlux

### with waiting for the persisted entity (blocking)

best result:
```
$ echo "POST http://localhost:9001" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=500 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         2409, 240.87, 60.15
Duration      [total, attack, wait]             40s, 10.001s, 29.998s
Latencies     [min, mean, 50, 90, 95, 99, max]  363.456ms, 2.185s, 1.906s, 3.959s, 5.446s, 6.466s, 30.001s
Bytes In      [total, mean]                     263559, 109.41
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           99.88%
Status Codes  [code:count]                      0:3  200:2406
Error Set:
Post http://localhost:9001: net/http: request canceled (Client.Timeout exceeded while awaiting headers)
```

### without waiting for the persisted entity (non-blocking)
best result:
```
$ echo "POST http://localhost:9001" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=500 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         18055, 1802.60, 1779.93
Duration      [total, attack, wait]             10.144s, 10.016s, 127.526ms
Latencies     [min, mean, 50, 90, 95, 99, max]  28.996ms, 278.045ms, 277.741ms, 397.419ms, 434.357ms, 628.272ms, 2.236s
Bytes In      [total, mean]                     1986050, 110.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:18055
Error Set:
```

# Results with _--max-workers=1500_
## Spring Web
```
$ echo "POST http://localhost:9002" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=1500 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         9702, 969.66, 798.89
Duration      [total, attack, wait]             12.144s, 10.006s, 2.139s
Latencies     [min, mean, 50, 90, 95, 99, max]  3.839ms, 1.703s, 1.678s, 2.529s, 2.768s, 3.287s, 4.966s
Bytes In      [total, mean]                     1076922, 111.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:9702
Error Set:
```
## Spring WebFlux (non-blocking)
```
$ echo "POST http://localhost:9001" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=1500 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         22368, 2235.84, 742.17
Duration      [total, attack, wait]             30.131s, 10.004s, 20.126s
Latencies     [min, mean, 50, 90, 95, 99, max]  18.996ms, 798.822ms, 354.211ms, 591.488ms, 692.709ms, 15.693s, 30.001s
Bytes In      [total, mean]                     2459820, 109.97
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           99.97%
Status Codes  [code:count]                      0:6  200:22362
Error Set:
Post http://localhost:9001: net/http: request canceled (Client.Timeout exceeded while awaiting headers)
```

# Results with _--max-workers=3000_
## Spring Web
```
$ echo "POST http://localhost:9002" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=3000 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         10537, 1053.16, 804.68
Duration      [total, attack, wait]             13.095s, 10.005s, 3.089s
Latencies     [min, mean, 50, 90, 95, 99, max]  3.548ms, 3.165s, 3.38s, 4.316s, 4.717s, 5.562s, 8.043s
Bytes In      [total, mean]                     1169607, 111.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:10537
Error Set:
```

## Spring WebFlux (non-blocking)
```
$ echo "POST http://localhost:9001" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=3000 | tee results.bin | ./vegeta.exe report
Requests      [total, rate, throughput]         33272, 3314.11, 1965.96
Duration      [total, attack, wait]             16.924s, 10.039s, 6.885s
Latencies     [min, mean, 50, 90, 95, 99, max]  23.998ms, 1.205s, 333.611ms, 622.406ms, 11.04s, 16.204s, 16.654s
Bytes In      [total, mean]                     3659920, 110.00
Bytes Out     [total, mean]                     0, 0.00
Success       [ratio]                           100.00%
Status Codes  [code:count]                      200:33272
Error Set:
```
