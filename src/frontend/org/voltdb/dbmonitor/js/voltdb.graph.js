﻿
(function(window) {

    var IMonitorGraphUI = (function () {
        
        this.Monitors = {};
        this.ChartCpu = nv.models.lineChart();
        this.ChartRam = nv.models.lineChart();
        this.ChartLatency = nv.models.lineChart();
        this.ChartTransactions = nv.models.lineChart();
        
        function getEmptyData() {
            var arr = [];
            var theDate = new Date();

            for (var i = 121; i >= 0; i--) {
                arr[i] = { x: new Date(theDate.getTime()), y:null };
                theDate.setSeconds(theDate.getSeconds() - 5);
            }

            return arr;
        }
        
        var dataCpu = [{
            "key": "CPU",
            "values": getDataCPU(),
            "color": "rgb(164, 136, 5)",
        }];

        var dataRam = [{
            "key": "RAM",
            "values": getEmptyData(),
            "color": "rgb(164, 136, 5)"
        }];

        var dataLatency = [{
            "key": "Latency",
            "values": getEmptyData(),
            "color": "rgb(27, 135, 200)"
        }];

        var dataTransactions = [{
            "key": "Transactions",
            "values": getEmptyData(),
            "color": "rgb(27, 135, 200)"
        }];

        nv.addGraph(function () {

            //Formats: http://www.d3noob.org/2012/12/formatting-date-time-on-d3js-graph.html
            //%b %d : Feb 01, %x : 02/01/2012, %X: HH:MM:ss
            MonitorGraphUI.ChartCpu.xAxis
                .tickFormat(function (d) {
                    return d3.time.format('%X')(new Date(d));
                });

            MonitorGraphUI.ChartCpu.xAxis.rotateLabels(-20);

            MonitorGraphUI.ChartCpu.yAxis
                .tickFormat(d3.format(',1%'));

            MonitorGraphUI.ChartCpu.yAxis
                .axisLabel('(%)')
                .axisLabelDistance(10);

            MonitorGraphUI.ChartCpu.tooltipContent(function (key, y, e, graph) {
                return '<h3> CPU </h3>'
                    + '<p>' + e + '% at ' + y + '</p>';
            });


            MonitorGraphUI.ChartCpu.margin({ left: 80 });

            //d3.select('#chart svg')
            d3.select('#visualisationCpu')
                .datum(dataCpu)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartCpu);

            nv.utils.windowResize(MonitorGraphUI.ChartCpu.update);

            return MonitorGraphUI.ChartCpu;
        });

        nv.addGraph(function () {
            MonitorGraphUI.ChartRam.xAxis
                .tickFormat(function (d) {
                    return d3.time.format('%X')(new Date(d));
                });
            
            MonitorGraphUI.ChartRam.xAxis.rotateLabels(-20);

            MonitorGraphUI.ChartRam.yAxis
                .tickFormat(d3.format(',.4f'));

            MonitorGraphUI.ChartRam.yAxis
                .axisLabel('(GB)')
                .axisLabelDistance(10);
            
            MonitorGraphUI.ChartRam.margin({ left: 80 });
            
            MonitorGraphUI.ChartRam.tooltipContent(function (key, y, e, graph) {
                return '<h3> RAM </h3>'
                    + '<p>' + e + 'GB at ' + y + '</p>';
            });
            
            d3.select('#visualisationRam')
                .datum(dataRam)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartRam);

            nv.utils.windowResize(MonitorGraphUI.ChartRam.update);

            return MonitorGraphUI.ChartRam;
        });

        nv.addGraph(function () {

            MonitorGraphUI.ChartLatency.xAxis
                .tickFormat(function (d) {
                    return d3.time.format('%X')(new Date(d));
                });

            MonitorGraphUI.ChartLatency.xAxis.rotateLabels(-20);
            
            MonitorGraphUI.ChartLatency.yAxis
                .tickFormat(d3.format(',.2f'));

            MonitorGraphUI.ChartLatency.yAxis
                .axisLabel('(ms)')
                .axisLabelDistance(10);

            MonitorGraphUI.ChartLatency.margin({ left: 80 });
            
            MonitorGraphUI.ChartLatency.tooltipContent(function (key, y, e, graph) {
                return '<h3> Latency </h3>'
                    + '<p>' + e + 'ms at ' + y + '</p>';
            });

            d3.select('#visualisationLatency')
                .datum(dataLatency)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartLatency);

            nv.utils.windowResize(MonitorGraphUI.ChartLatency.update);

            return MonitorGraphUI.ChartLatency;
        });

        nv.addGraph(function () {

            MonitorGraphUI.ChartTransactions.xAxis
                .tickFormat(function (d) {
                    return d3.time.format('%X')(new Date(d));
                });
            
            MonitorGraphUI.ChartTransactions.xAxis.rotateLabels(-20);
            
            MonitorGraphUI.ChartTransactions.yAxis
                .tickFormat(d3.format(',.2f'));

            MonitorGraphUI.ChartTransactions.yAxis
                .axisLabel('(Transactions/s)')
                .axisLabelDistance(10);

            MonitorGraphUI.ChartTransactions.margin({ left: 80 });

            MonitorGraphUI.ChartTransactions.tooltipContent(function (key, y, e, graph) {
                return '<h3> Transactions </h3>'
                    + '<p>' + e + 'tps at ' + y + '</p>';
            });

            d3.select('#visualisationTransaction')
                .datum(dataTransactions)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartTransactions);
            
            nv.utils.windowResize(MonitorGraphUI.ChartTransactions.update);

            return MonitorGraphUI.ChartTransactions;
        });

        function getDataCPU() {

            //Format for date: Date(year, month, day, hours, minutes, seconds, milliseconds)

            var lineData = [
                {
                    'x': new Date(2012, 01, 01, 10, 5, 6, 0),
                    'y': 0.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 7, 0),
                    'y': 1
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 8, 0),
                    'y': 1.3
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 8, 100),
                    'y': 2.3
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 9, 0),
                    'y': 1.2
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 10, 0),
                    'y': 0.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 11, 0),
                    'y': 0.9
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 12, 0),
                    'y': 2.0
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 13, 0),
                    'y': 2.3
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 14, 0),
                    'y': 1.6
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 16, 0),
                    'y': 1.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 17, 0),
                    'y': 1.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 18, 0),
                    'y': 1.9
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 19, 0),
                    'y': 3.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 21, 0),
                    'y': 8.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 22, 0),
                    'y': 4.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 23, 0),
                    'y': 2.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 24, 0),
                    'y': 1.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 25, 0),
                    'y': 3.7
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 26, 0),
                    'y': 3.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 27, 0),
                    'y': 2.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 28, 0),
                    'y': 1.8
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 29, 0),
                    'y': 1.0
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 30, 0),
                    'y': 2.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 31, 0),
                    'y': 1.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 32, 0),
                    'y': 3.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 33, 0),
                    'y': 8.0
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 34, 0),
                    'y': 2.8
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 35, 0),
                    'y': 2.9
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 36, 0),
                    'y': 4.0
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 37, 0),
                    'y': 4.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 38, 0),
                    'y': 2.9
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 39, 0),
                    'y': 3.3
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 40, 0),
                    'y': 4.9
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 41, 0),
                    'y': 5.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 42, 0),
                    'y': 2.6
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 43, 0),
                    'y': 3.6
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 44, 0),
                    'y': 2.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 45, 0),
                    'y': 3.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 46, 0),
                    'y': 3.6
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 47, 0),
                    'y': 3.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 48, 0),
                    'y': 2.0
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 49, 0),
                    'y': 2.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 50, 0),
                    'y': 4.1
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 51, 0),
                    'y': 3.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 52, 0),
                    'y': 4.0
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 53, 0),
                    'y': 4.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 54, 0),
                    'y': 2.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 55, 0),
                    'y': 2.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 56, 0),
                    'y': 3.5
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 57, 0),
                    'y': 5.8
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 58, 0),
                    'y': 5.0
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 59, 0),
                    'y': 3.0
                },

                {
                    'x': new Date(2012, 01, 01, 10, 5, 60, 0),
                    'y': 3.8
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 61, 0),
                    'y': 2.5
                },
                {
                    'x': new Date(2012, 01, 01, 10, 5, 62, 0),
                    'y': 5
                }];

            return lineData;
        }

        function Histogram(lowestTrackableValue, highestTrackableValue, nSVD, totalCount) {
            this.lowestTrackableValue = lowestTrackableValue;
            this.highestTrackableValue = highestTrackableValue;
            this.nSVD = nSVD;
            this.totalCount = totalCount;
            this.count = [];
            this.init();
        }

        Histogram.prototype.init = function() {
            var largestValueWithSingleUnitResolution = 2 * Math.pow(10, this.nSVD);
            this.unitMagnitude = Math.floor(Math.log(this.lowestTrackableValue) / Math.log(2));
            var subBucketCountMagnitude = Math.ceil(Math.log(largestValueWithSingleUnitResolution) / Math.log(2));
            this.subBucketHalfCountMagnitude = ((subBucketCountMagnitude > 1) ? subBucketCountMagnitude : 1) - 1;
            this.subBucketCount = Math.pow(2, (this.subBucketHalfCountMagnitude + 1));
            this.subBucketHalfCount = this.subBucketCount / 2;
            this.subBucketMask = (this.subBucketCount - 1) << this.unitMagnitude;
            var trackableValue = (this.subBucketCount - 1) << this.unitMagnitude;
            var bucketsNeeded = 1;
            while (trackableValue < this.highestTrackableValue) {
                trackableValue *= 2;
                bucketsNeeded++;
            }
            this.bucketCount = bucketsNeeded;

            this.countsArrayLength = (this.bucketCount + 1) * (this.subBucketCount / 2);
        };

        Histogram.prototype.diff = function(newer) {
            var h = new Histogram(newer.lowestTrackableValue, newer.highestTrackableValue, newer.nSVD, newer.totalCount - this.totalCount);
            for (var i = 0; i < h.countsArrayLength; i++) {
                h.count[i] = newer.count[i] - this.count[i];
            }
            return h;
        };

        Histogram.prototype.getCountAt = function(bucketIndex, subBucketIndex) {
            var bucketBaseIndex = (bucketIndex + 1) << this.subBucketHalfCountMagnitude;
            var offsetInBucket = subBucketIndex - this.subBucketHalfCount;
            var countIndex = bucketBaseIndex + offsetInBucket;
            return this.count[countIndex];
        };

        Histogram.prototype.valueFromIndex = function(bucketIndex, subBucketIndex) {
            return subBucketIndex * Math.pow(2, bucketIndex + this.unitMagnitude);
        };

        Histogram.prototype.getValueAtPercentile = function(percentile) {
            var totalToCurrentIJ = 0;
            var countAtPercentile = Math.floor(((percentile / 100.0) * this.totalCount) + 0.5); // round to nearest
            for (var i = 0; i < this.bucketCount; i++) {
                var j = (i == 0) ? 0 : (this.subBucketCount / 2);
                for (; j < this.subBucketCount; j++) {
                    totalToCurrentIJ += this.getCountAt(i, j);
                    if (totalToCurrentIJ >= countAtPercentile) {
                        var valueAtIndex = this.valueFromIndex(i, j);
                        return valueAtIndex / 1000.0;
                    }
                }
            }
        };

        function read32(str) {
            var s1 = str.substring(0, 2);
            var s2 = str.substring(2, 4);
            var s3 = str.substring(4, 6);
            var s4 = str.substring(6, 8);
            return s4 + s3 + s2 + s1;
        }

        function read64(str) {
            var s1 = read32(str);
            var s2 = read32(str.substring(8, 16));
            return s2 + s1;
        }

        function convert2Histogram(str) {
            // Read lowestTrackableValue
            var lowestTrackableValue = parseInt(read64(str), 16);
            str = str.substring(16, str.length);

            // Read highestTrackableValue
            var highestTrackableValue = parseInt(read64(str), 16);
            str = str.substring(16, str.length);

            // Read numberOfSignificantValueDigits
            var nSVD = parseInt(read32(str), 16);
            str = str.substring(8, str.length);

            // Read totalCount
            var totalCount = parseInt(read64(str), 16);
            str = str.substring(16, str.length);

            var histogram = new Histogram(lowestTrackableValue, highestTrackableValue, nSVD, totalCount);

            var i = 0;
            while (str.length >= 16) {
                var value = parseInt(read64(str), 16);
                histogram.count[i] = value;
                str = str.substring(16, str.length);
                i++;
            }
            return histogram;
        }

        this.AddGraph = function () {
            MonitorGraphUI.Monitors = {
                
                'latHistogram': null,
                'latData': getEmptyData(),
                'tpsData': getEmptyData(),
                'memData': getEmptyData(),
                'cpuData': getEmptyData(),
                'lastTimedTransactionCount': -1,
                'lastTimerTick': -1
            };
        };

        this.RefreshLatency = function(latency) {

            var monitor = MonitorGraphUI.Monitors;
            var dataLat = monitor.latData;
            var strLatStats = "";
            var timeStamp;
            // Compute latency statistics
            jQuery.each(latency, function (id, val) {
                strLatStats += val["UNCOMPRESSED_HISTOGRAM"];
                timeStamp = val["TIMESTAMP"];
            });

            var latStats = convert2Histogram(strLatStats);
            
            var lat = 0;
            if (monitor.latHistogram == null)
                lat = latStats.getValueAtPercentile(99);
            else
                lat = monitor.latHistogram.diff(latStats).getValueAtPercentile(99);

            lat = parseFloat(lat).toFixed(1) * 1;
            
            monitor.latHistogram = latStats;
            dataLat = dataLat.slice(1);
            dataLat.push({ 'x': new Date(timeStamp), 'y': lat });
            dataLatency[0]["values"] = dataLat;
            MonitorGraphUI.Monitors.latData = dataLat;
            
            d3.select("#visualisationLatency")
               .datum(dataLatency)
               .transition().duration(500)
               .call(MonitorGraphUI.ChartLatency);
            
        };

        this.RefreshMemory = function (memoryDetails, currentServer) {
            var monitor = MonitorGraphUI.Monitors;
            var dataMem = monitor.memData;

            var memDetails = memoryDetails;
            var memRss = parseFloat(memDetails[currentServer].RSS * 1.0 / 1048576.0).toFixed(3) * 1;
            var memTimeStamp = new Date(memDetails[currentServer].TIMESTAMP);

            dataMem = dataMem.slice(1);
            dataMem.push({ 'x': new Date(memTimeStamp), 'y' : memRss });
            dataRam[0]["values"] = dataMem;
            MonitorGraphUI.Monitors.memData = dataMem;
            
            d3.select('#visualisationRam')
                .datum(dataRam)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartRam);

        };

        this.RefreshTransaction = function(transactionDetails) {
            var monitor = MonitorGraphUI.Monitors;
            var datatrans = monitor.tpsData;

            var transacDetail = transactionDetails;
            var currentTimedTransactionCount = transacDetail["CurrentTimedTransactionCount"];
            var currentTimerTick = transacDetail["currentTimerTick"];

            
            if (monitor.lastTimedTransactionCount > 0 && monitor.lastTimerTick > 0 && monitor.lastTimerTick != currentTimerTick) {
                var delta = currentTimedTransactionCount - monitor.lastTimedTransactionCount;
                datatrans = datatrans.slice(1);
                datatrans.push({ "x": new Date(transacDetail["TimeStamp"]), "y": parseFloat(delta * 1000.0 / (currentTimerTick - monitor.lastTimerTick)).toFixed(1) * 1 });
            }
            
            dataTransactions[0]["values"] = datatrans;
            MonitorGraphUI.Monitors.tpsData = datatrans;

            monitor.lastTimedTransactionCount = currentTimedTransactionCount;
            monitor.lastTimerTick = currentTimerTick;

            d3.select('#visualisationTransaction')
                .datum(dataTransactions)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartTransactions);

        };

        this.RefreshCpu = function(cpuDetails) {
            var monitor = MonitorGraphUI.Monitors;
            var cpuData = monitor.cpuData;

            var cpuDetail = cpuDetails;
            cpuData = cpuData.slice(1);
            cpuData.push({ "x": new Date(new Date().getTime()), "y": 0 });
            dataCpu[0]["values"] = cpuData;
            MonitorGraphUI.Monitors.cpuData = cpuData;

            d3.select('#visualisationCpu')
                .datum(dataCpu)
                .transition().duration(500)
                .call(MonitorGraphUI.ChartCpu);
        };
    });
    
    window.MonitorGraphUI = MonitorGraphUI = new IMonitorGraphUI();
})(window);

$(document).ready(function () {
  
    MonitorGraphUI.AddGraph();
});

