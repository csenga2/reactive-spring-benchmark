# reactive-spring-benchmark

docker compose command:
```
docker-compose --compatibility up -d --build
```
# Spring Web

vegeta command:
```
echo "POST http://localhost:9002" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=500 | tee results.bin | ./vegeta.exe report
```

best result:
```
MINGW64 /c/vegeta  
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

# Spring WebFlux

vegeta command:
```
echo "POST http://localhost:9001" | ./vegeta.exe attack -duration=10s -rate=0 --max-workers=500 | tee results.bin | ./vegeta.exe report
```
## with waiting for the persisted entity

best result:
```
MINGW64 /c/vegeta
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

## without waiting for the persisted entity
best result:
```
MINGW64 /c/vegeta
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
