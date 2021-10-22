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
        allAnn()
    }
}


allAnn()

// Announcement List - Start
function allAnn(){
    const pageSize = $("#cPage").val()
    pageCount(1);
    $.ajax({
        url: './announcement/announcementList/'+pageNumber+'/'+pageSize,
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
        const status = itm.astatus == 1? 'Aktif' : 'Pasif';
        html += `<tr>
                    <td style="text-align: center">`+status+`</td>
                    <td style="text-align: center">`+itm.atitle+`</td>
                    <td style="text-align: center">`+itm.adetail+`</td>
                    <td style="text-align: center">`+dateformat(itm.adate)+`</td>
                    <td style="text-align: center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button onclick="fncAnnouncementDelete('`+itm.raid+`')"  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                           <a href="/announcement/update/`+itm.raid+`"><button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button> </a>
                        </div>
                    </td>
                </tr>`;
    }
    $('#annRow').html(html);

}
// Announcement List - End


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

// Announcement Delete - start
function fncAnnouncementDelete( raid ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/announcement/delete/'+raid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allAnn();
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
// Announcement Delete - end

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
        allAnn()
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
        allAnn()
    }

}












// Elastic Search - start
function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/announcement/search/'+pageNumber+'/'+pageSize +'/'+asearch,
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
        allAnn()
    }
}
// Elastic Search - end

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './announcement/announcementList/pageCount/'+pageSize+'/'+countStatus,
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
























