// 과거 날짜 검색 제한
var now_utc = Date.now() // 지금 날짜를 밀리초로, getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
var timeOff = new Date().getTimezoneOffset() * 60000; // 분단위를 밀리초로 변환
var year = new Date(now_utc - timeOff).toISOString().split("-")[0]; //'yyyy-mm-ddT18:09:38.134Z'에서 년 추출
var month = new Date(now_utc - timeOff).toISOString().split("-")[1]; //월 추출
var thisMonth = year + "-" + month; // 현재시점의 "yyyy-mm"
document.getElementById("month").setAttribute("min", thisMonth); // 월 검색에 현재 월 이후만 표시


// 혼잡도에 따른 글씨 색 변화주기
document.addEventListener('DOMContentLoaded', function() {
    var contentCongestion = document.getElementById("contentCongestion");
    if (contentCongestion) {
        var textValue = contentCongestion.textContent.trim();
        colorChange(contentCongestion, textValue);
    } else {
        console.error("contentCongestion 요소를 찾을 수 없습니다.");
    }
});

function colorChange(element, textValue) {
    // 모든 색상 클래스 제거
    element.classList.remove("green", "blue", "orange", "red");
    
    switch (textValue) {
        case "적정":
            element.classList.add("green");
            break;
        case "보통":
            element.classList.add("blue");
            break;
        case "혼잡":
            element.classList.add("orange");
            break;
        case "매우 혼잡":
            element.classList.add("red");
            break;
        default:
            console.warn("알 수 없는 혼잡도:", textValue);
    }
}


// 웅급실 차트 그리기
var myChart1;
var myChart2;

window.onload = function() {

	//응급실 차트 변수 선언
	var ctx1 = document.getElementById('erChart1').getContext('2d');
	var ctx2 = document.getElementById('erChart2').getContext('2d');

	// 초기 데이터셋
	var initialData = {
			labels: [""],
			datasets: [{
				label: '전국',
				backgroundColor: 'rgba(75, 192, 192, 0.7)',
				data: [congestionValue]
			}]
	};


	// 차트 그리기(초기)
	myChart1 = new Chart(ctx1, {
		type: 'bar',
		data: initialData,
		options: {
			indexAxis: 'y',
			scales: {
				x: {
					stacked: false
				},
				y: {
					stacked: false
				}
			}
		}
	});

	myChart2 = new Chart(ctx2, {
		type: 'bar',
		data: initialData,
		options: {
			scales: {
				x: {
					stacked: false
				},
				y: {
					stacked: false
				}
			}
		}
	});
};


//지도 생성
const container = document.getElementById('map');
const options = {
    center: new kakao.maps.LatLng(36, 127.5),
    level: 13,
    disableDoubleClickZoom: true
};
const map = new kakao.maps.Map(container, options);

const infowindow = new kakao.maps.InfoWindow({ removable: true });

map.setZoomable(false); // 확대 막기
map.setCursor('default');

//지도 반응형
var ro = new ResizeObserver(entries => {
    for (let entry of entries) {
        map.relayout();
        centerReset();
    }
});

ro.observe(mapWrap);

function centerReset() {
    var moveLatLon = new kakao.maps.LatLng(36, 127.5);
    map.setCenter(moveLatLon);
}


// 시도 좌표
fetch('/goldenTime/js/sido.json') 
	.then(response => response.json())
	.then(data => {
		let geojson = data;
      	let dataFeatures = geojson.features; // geojson에서 필요한 값(features)만 추출

     	var coordinates = []; // 좌표 저장할 배열
     	var name = ''; // 지역 이름
     	var eng_name = ''; // 지역 영어 이름
     	
     	// data에서 좌표 끌고와 polygon&이벤트 만들기
      	dataFeatures.forEach((val) => {
        	coordinates = val.geometry.coordinates;
        	name = val.properties.SIG_KOR_NM;
        	eng_name = val.properties.CTP_ENG_NM;
        	displayArea(coordinates, name, eng_name); // displayArea 함수 호출
      	});
  	})
  	.catch(error => console.error('Error loading geojson:', error));


// polygon 만들고 마우스 이벤트 주는 메소드
function displayArea(coordinates, name, eng_name) {
    let path = [];
    let points = [];

    coordinates[0].forEach((coordinate) => {
        path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
        points.push({ x: coordinate[1], y: coordinate[0] });
    });
    
    //polygon 생성
    let polygon = new kakao.maps.Polygon({
        map: map,
        path: path, // 그려질 다각형의 좌표 배열
        strokeWeight: 2, // 선 두께
        strokeColor: '#004c80', // 선 색깔
        strokeOpacity: 0.5, // 선 불투명도
        strokeStyle: 'solid', // 선 스타일
        fillColor: '#ffffff', // 채우기 색깔
        fillOpacity: 0.8, // 채우기 불투명도
    });

    //폴리곤 중심좌표 구하는 메소드(centroid 알고리즘)
    function centerMap(points) {
        var area = 0, x = 0, y = 0;

        for (let i = 0, len = points.length, j = len - 1; i < len; j = i++) {
            const p1 = points[i];
            const p2 = points[j];
            const f = p1.y * p2.x - p2.y * p1.x;
            x += (p1.x + p2.x) * f;
            y += (p1.y + p2.y) * f;
            area += f * 3;
        }
        return new kakao.maps.LatLng(x / area, y / area);
    }

    //오버레이 생성 메소드
    function overlaySet(name, points) {
        var content = '<div class="area" style="font-weight:bold; font-size:10px; color:black;">' + name + '</div>';

        // 커스텀 오버레이가 표시될 위치
        var position = centerMap(points);

        // 커스텀 오버레이 생성
        var customOverlay = new kakao.maps.CustomOverlay({
            position: position,
            content: content,
            xAnchor: 0.3,
            yAnchor: 0.91
        });

        // 커스텀 오버레이를 지도에 표시
        customOverlay.setMap(map);
    }
    
    overlaySet(name, points);  // 오버레이 생성

    // mouseover 시 폴리곤의 채움색 변경
    kakao.maps.event.addListener(polygon, 'mouseover', function (mouseEvent) {
        polygon.setOptions({ fillColor: '#09f' });
    });

    // mouseout 시 폴리곤의 채움색을 원래색으로 변경
    kakao.maps.event.addListener(polygon, 'mouseout', function () {
        polygon.setOptions({ fillColor: '#ffffff' });
    });

    //다각형 클릭 이벤트 설정
    kakao.maps.event.addListener(polygon, 'click', function () {
		console.log('Clicked on polygon:', name);
		
        var regionTitle = document.querySelector("#regionTitle");
        regionTitle.innerHTML = name;
        var contentRegion = document.querySelector("#contentRegion");
        contentRegion.innerHTML = name;        
        
        //사용자가 선택한 연도와 월 가져오기
        var selectedDate = document.querySelector("#month").value;
        var selectedYear = selectedDate.split("-")[0]; // 연도 추출 ('yyyy')
		var selectedMonth = selectedDate.split("-")[1]; // 월 추출 ('MM')
        
        //fetch API로 클릭한 지역(오버레이)의 혼잡도 끌어오기
        //var url = `regionSearch/${year}${month}${eng_name}`;
        //var url = `regionSearch/${year}${month}?regionName=${name}&regionEngName=${eng_name}`;
        var url = `regionSearch/${selectedYear}${selectedMonth}?regionName=${name}&regionEngName=${eng_name}`;
        console.log('Request URL:', url);
        
    	fetch(url, {
    	    method: "GET",
    	    headers: {
    	        "Content-Type": "application/json",
    	    }
    	})
    	.then(response => {
    		console.log('Response status:', response.status);
    		if(!response.ok) {
    			throw new Error(`HTTP error! Status: ${response.status}`);
    		}
    		//return response.json();
    		return response.text(); // 먼저 텍스트로 응답을 받음
    	})
    	.then(responseText => {
    		/*console.log('Data received:', data);
    		const congestionValue = data[0];
    		const congestion = data[1];*/

		    try {
		        const data = JSON.parse(responseText); // 텍스트를 JSON으로 파싱
		        console.log('Data received:', data); // 수신 데이터 로그
		        const congestionValue = data[0];
		        const congestion = data[1];
    		
	    	    var contentCongestion = document.querySelector("#contentCongestion");
	    	    contentCongestion.innerHTML = congestion; // 혼잡도 표시
	    	    colorChange(contentCongestion, congestion); // 혼잡도에 따른 글씨 색 변화
	    	    
	            //응급실 차트에 비교 데이터 추가
	            if (myChart1.data.datasets.length > 1) {
	                myChart1.data.datasets.pop();
	            }
	
	            // 새로운 데이터셋 추가
	            myChart1.data.datasets.push({
	                label: name,
	                backgroundColor: 'rgba(255, 99, 132, 0.7)',
	                data: [congestionValue]
	            });
	
	            myChart1.update();
	            myChart2.update();
	    	    
	    	    console.log("혼잡도 수치 : " + congestionValue);
	    	    console.log("결과 : " + congestion);
    	    } catch (e) {
		        console.error('JSON parsing error:', e);
		        console.error('Response text:', responseText);
		    }
    	})
    	.catch(error => {
    	    console.error('Fetch error:', error);
    	});
    });
};


// 응급실 지표 팝업창 띄우기
var popOpen = document.querySelector("#popOpen");
popOpen.addEventListener("click", function () {
    var pop = document.querySelector("#popWrap");
    pop.classList.remove('hide');
})

var popClose = document.querySelector("#popClose");
popClose.addEventListener("click", function () {
    var pop = document.querySelector("#popWrap");
    pop.classList.add('hide');
})


//병상포화지수 토글
$(document).ready(function () {
    $("#formula").click(function () {
        $("#formulaPop").toggle();
    });
});