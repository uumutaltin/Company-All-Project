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
        allCustomer()
    }
}

allCustomer()

// Content List - Start
function allCustomer(){
    const pageSize = $("#cPage").val()
    const status = $("#cStatus").val()
    pageCount(1);
    $.ajax({
        url: './customer/customerList/'+pageNumber+'/'+pageSize,
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
        if (itm.ban == 0){
            html += `<tr>
            <td>`+itm.cid+`</td>
            <td>`+itm.cname+`</td>
            <td>`+itm.csurname+`</td>
            <td>`+itm.email+`</td>
            <td>`+itm.mobile_phone+`</td>
           <td class="text-center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">

                            <button onclick="fncCustomerBan(`+!itm.ban+`,'`+itm.rcid+`')"  type="button" class="btn btn-outline-primary "><i class="fas fa-ban"></i></button>
                        </div>
                    </td>
                    <td class="text-center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button onclick="fncCustomerDelete('`+itm.rcid+`')"  type="button" class="btn btn-outline-primary "><i class="fas fa-user-times"></i></button>
                        </div>
                    </td>
            </tr>`;

        }

        else{
            html += `<tr>
            <td>`+itm.cid+`</td>
            <td>`+itm.cname+`</td>
            <td>`+itm.csurname+`</td>
            <td>`+itm.email+`</td>
            <td>`+itm.mobile_phone+`</td>
           <td class="text-center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">

                            <button onclick="fncCustomerBan(`+!itm.ban+`,'`+itm.rcid+`')"  type="button" class="btn btn-outline-primary "><i class="fas fa-user"></i></button>
                        </div>
                    </td>
                    <td class="text-center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button onclick="fncCustomerDelete('`+itm.rcid+`')"  type="button" class="btn btn-outline-primary "><i class="fas fa-user-times"></i></button>
                        </div>
                    </td>
            </tr>`;
        }


    }
    $('#customerRow').html(html);

}



function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/customer/search/'+pageNumber+'/'+pageSize +'/'+asearch,
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
        allCustomer()
    }
}



function fncCustomerDelete( rcid ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/customer/delete/'+rcid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allCustomer()
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

function fncCustomerBan(ban, rcid) {
    let status
    if(ban ==0){
        status=false
    }else {
        status=true
    }
    $.ajax({
        url: '/customer/ban/'+ban+'/'+rcid,
        type: 'GET',
        dataType: 'text',
        success: function (data) {
            if ( data != "0" ) {
            allCustomer()
            }
        },
        error: function (err) {
            console.log(err)
        }
    })
}

function pagePlus(){
    const pageSize = $("#cPage").val()
    let plusNumber = globalArr.length
    let pageNumberx = pageNumber
    if( plusNumber < pageSize ){
        pageNumber = pageNumberx
    }
    else{
        pageNumber++
    }

    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allCustomer()
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
        allCustomer()
    }

}

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './customer/customerList/pageCount/'+pageSize+'/'+countStatus,
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

