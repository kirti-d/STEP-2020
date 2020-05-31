
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

/** Fetches section data and uses it to create a chart. */
function drawChart() {
  fetch('/chart').then(response => response.json())
  .then((sectionLiking) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'section');
    data.addColumn('number', 'count');
    Object.keys(sectionLiking).forEach((section) => {
      data.addRow([section, sectionLiking[section]]);
    });

    const options = {
      'title': 'Favorite Section',
      'width':600,
      'height':500
    };

    const chart = new google.visualization.ColumnChart(
        document.getElementById('chart-container'));
    chart.draw(data, options);
  });
}

// loads map 
function initMap() {
  // The location of college
  var college = {lat: 28.751348, lng: 77.118107};
  // The map, centered at college
  var map = new google.maps.Map(
      document.getElementById('map'), {zoom: 8, center: college});
  // The marker, positioned at DTU(college)
  var marker = new google.maps.Marker({position: college,label:'DTU', map: map});
}