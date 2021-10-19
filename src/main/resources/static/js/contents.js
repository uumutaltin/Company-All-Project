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
        allContent()
    }
}

allContent()

// Content List - Start
function allContent(){
    const pageSize = $("#cPage").val()
    const status = $("#cStatus").val()
    pageCount(1);
    $.ajax({
        url: './contents/contentList/'+pageNumber+'/'+pageSize,
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

function createRow(data){
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data [i];

        const status = itm.cstatus == 1? 'Aktif' : 'Pasif';
        html += `<tr>
            <td>`+status+`</td>
            <td>`+itm.ctitle+`</td>
            <td>`+itm.cdescription+`</td>
            <td>`+itm.cdetail+`</td>
            <td>`+dateformat(itm.cdate)+`</td>
            <td >
                <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                    <button onclick="fncContentsDelete('`+itm.rcid+`')"  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                    <a href="/contents/update/`+itm.rcid+`" ><button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button></a>
                </div>
            </td>
            </tr>`;
    }
    $('#contentRow').html(html);

}

// Content List - End

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
        allContent()
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
        allContent()
    }

}







// Contents delete - start
function fncContentsDelete( rcid ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/contents/delete/'+rcid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allContent()
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
// Contents delete - end

// Date Format - Start
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
// Date Format - End








// elastic search - start
function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/contents/search/'+pageNumber+'/'+pageSize +'/'+asearch,
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
        allContent()
    }
}

// // elastic search - end


pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './contents/contentList/pageCount/'+pageSize+'/'+countStatus,
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

function cstatusChange(){

    const pageSize = $("#cPage").val()
    const status = $("#cStatus").val()
    if(status == 0){
        allContent()
    }else {

        $.ajax({
            url: './contents/contentList/'+pageNumber+'/'+pageSize + '/'+status,
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

}

