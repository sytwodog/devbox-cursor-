// 初始化学员增长趋势图表
function initStudentGrowthChart() {
    const chart = echarts.init(document.getElementById('student-growth-chart'));
    
    const option = {
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '学员数量',
            type: 'line',
            smooth: true,
            data: [150, 230, 224, 218, 335, 347, 330, 420, 432, 445, 460, 470],
            itemStyle: {
                color: '#1a73e8'
            },
            areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(26,115,232,0.3)'
                }, {
                    offset: 1,
                    color: 'rgba(26,115,232,0.1)'
                }])
            }
        }]
    };
    
    chart.setOption(option);
}

// 初始化课程分布图表
function initCourseDistributionChart() {
    const chart = echarts.init(document.getElementById('course-distribution-chart'));
    
    const option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [{
            name: '课程分布',
            type: 'pie',
            radius: '70%',
            data: [
                { value: 20, name: 'Java开发' },
                { value: 15, name: 'Web前端' },
                { value: 12, name: 'Python' },
                { value: 9, name: '大数据' }
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    };
    
    chart.setOption(option);
}

// 页面加载完成后初始化图表
window.addEventListener('load', () => {
    initStudentGrowthChart();
    initCourseDistributionChart();
});

// 监听窗口大小变化，重新调整图表大小
window.addEventListener('resize', () => {
    const studentGrowthChart = echarts.getInstanceByDom(document.getElementById('student-growth-chart'));
    const courseDistributionChart = echarts.getInstanceByDom(document.getElementById('course-distribution-chart'));
    
    if (studentGrowthChart) {
        studentGrowthChart.resize();
    }
    if (courseDistributionChart) {
        courseDistributionChart.resize();
    }
});
