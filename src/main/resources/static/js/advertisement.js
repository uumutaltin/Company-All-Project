let pageNumber = 0
let globalArr = []
let lastPageNumber = 0;

function changeVariables(dataNumber){
    if (dataNumber == -5) {
        dataNumber = lastPageNumber-1;

    }
    pageNumber = dataNumber;
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }
}

allAd()

function allAd( ){
    const pageSize = $("#cPage").val()
    pageCount(1);
    /*    lastPage()*/
    $.ajax({
        url: './advertisement/advertList/'+pageNumber+'/'+pageSize,
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data)
            createRow(data)
        },
        error: function (err){
            console.log(err)
        }
    })
}

function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data [i];

        const status = itm.astatus == 1? 'Aktif' : 'Pasif';
        html += `<tr>
          <td>`+itm.atitle +`</td>
          <td>`+itm.adesc +`</td>
          <td>`+itm.adesc +`</td>
          <td>`+dateformat(itm.datestart) +`</td>
          <td>`+dateformat(itm.dateend) +`</td>
          <td><a target="_blank" href="http://localhost:8090/uploads/advertisement/`+itm.advertimage +`"><img style="width: 100px; height: 100px;" src="../uploads/advertisement/`+itm.advertimage +`"> </img></a></td>
          <td>`+itm.advertwidth +`</td>
          <td>`+itm.advertheight +`</td>
          <td>`+itm.advertlink +`</td>
          <td>`+ status +`</td>
          <td >
            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
               <button onclick="fncAdvertisementDelete('`+itm.raid+`')"  type="button" class="btn btn-outline-danger "><i class="far fa-trash-alt"></i></button>
              <a href="/advertisement/update/`+itm.raid+`"><button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button></a>
            </div>
          </td>
        </tr>`;
    }
    $('#adRow').html(html);
}




function pagePlus(){
    const pageSize = $("#cPage").val()
    let plusNumber = globalArr.length
    let pageNumberx = pageNumber
    console.log('plusNumber : ' + plusNumber)
    if( plusNumber < pageSize ){
        pageNumber = pageNumberx
    }
    else{
        pageNumber++
    }

    console.log('GlobalArr Length : '+plusNumber)
    /*    lastPage()*/
    console.log(pageNumber)
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }
}
function pageMinus(){
    console.log('GlobalArr Length : '+globalArr.length)
    if(pageNumber <= 0){
        pageNumber=0
    }else {
        pageNumber--
    }
    /*    lastPage()*/
    console.log(pageNumber)
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }

}

/*function lastPage(){
    const pageSize = $("#cPage").val()
    const globallenght = globalArr.length
    const total = globallenght / pageSize
    $("#totalPageNumber").text(pageNumber+1 +'/'+ total)


}*/

// Advertisement delete - start
function fncAdvertisementDelete( raid ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/advertisement/delete/'+raid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allAd();
                }else {
                    alert("Silme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}
// Advertisement delete - end

// elastic search - start
function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/advertisement/search/'+pageNumber+'/'+pageSize +'/'+asearch,
            type: 'GET',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log(data)
                pageCount(2)
                createRow(data)
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
    else {
        allAd()
    }
}

// elastic search - end,

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './advertisement/advertList/pageCount/'+pageSize+'/'+countStatus,
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data)
            $("#totalPageNumber").text(pageNumber+1 + '/' + data)
            lastPageNumber = data;
        },
        error: function (err){
            console.log(err)
        }
    })
}
function dateformat(dataDate){
    dataDate = dataDate.substring(0,dataDate.indexOf('+'));
    dateData = dataDate+'Z';
    console.log(dateData);
    date = new Date(dateData);
    year = date.getFullYear();
    month = date.getMonth()+1;
    dt = date.getDate();

    if (dt < 10) {
        dt = '0' + dt;
    }
    if (month < 10) {
        month = '0' + month;
    }
    finalDate = year+'-' + month + '-'+dt
    console.log(finalDate);
    return finalDate;
}