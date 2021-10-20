let globalCatArr = []
let pageNumber = 0
let globalArr = []
let lastPageNumber = 0;

fncCategoryList();
// News Category List - start
function fncCategoryList(){
    $.ajax({
        url: '/news/category/list',
        type: 'GET',
        success: function (data) {
            console.log(data)
            createCategory(data)
        },
        error: function (err){
            console.log(err)
        }
    })
}

let ncat = $("#ncAll");
function createCategory( data ){
    ncat.find('option').remove().end();
    ncat.append(` <option value="-1" >-- Kategori Seçiniz</option>`);
    globalCatArr = data;
    for (let i = 0; i<data.length;i++){
        const itm = data[i];
        const st = itm.nctitle

        ncat.append(`<option data-subtext="" value="`+itm.ncid+`">`+st+`</option>`)
    }
    ncat.selectpicker("refresh");
}
// News Category List - end

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
        allNews()
    }
}







// News List - start

allNews()

function allNews( ){
    const pageSize = $("#cPage").val()
    pageCount(1);
    /*    lastPage()*/
    $.ajax({
        url: './news/newsList/'+pageNumber+'/'+pageSize,
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

        const status = itm.nstatus == 1? 'Aktif' : 'Pasif';
        html += `<tr>
                    <td>`+itm.ntitle +`</td>
                    <td>`+itm.newsCategory.nctitle +`</td>
                    <td>`+status+`</td>
                    <td><a target="_blank" href="http://localhost:8090/uploads/news/`+itm.newsimage +`"><img style="width: 100px; height: 100px;" src="../uploads/news/`+itm.newsimage +`"> </img></a></td>
                    <td>`+dateformat(itm.ndate)+`</td>
                    <td >
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button onclick="fncAdvertisementDelete('`+itm.rnid+`')"  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                           <a href="/news/update/`+itm.rnid+`"> <button type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button> </a>
                        </div>
                    </td>
                </tr>`;
    }
    $('#newsRow').html(html);
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
        allNews()
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
        allNews()
    }

}



// News List - end

// News delete - start
function fncAdvertisementDelete( rnid ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/news/delete/'+rnid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allNews();
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
// News delete - end

// Elastic Search - start
function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/news/search/'+pageNumber+'/'+pageSize +'/'+asearch,
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
        allNews()
    }
}
// Elastic Search - end

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './news/newsList/pageCount/'+pageSize+'/'+countStatus,
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

function nFilterChange(){

    const pageSize = $("#cPage").val()
    const status = $("#nStatus").val()
    const ncid = $("#ncAll").val()

    let nstatus;
    if(status == 0){
        nstatus = false
    }else if(status == 1) {
        nstatus = true
    }


    if(status == -1){
        allNews()
    }else {

        $.ajax({
            url: './news/newsListFilter/'+pageNumber+'/'+pageSize + '/'+ nstatus +'/'+ncid,
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



















