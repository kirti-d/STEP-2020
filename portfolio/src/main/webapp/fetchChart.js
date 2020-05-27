
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