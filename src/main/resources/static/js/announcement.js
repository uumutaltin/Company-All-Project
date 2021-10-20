let pageNumber = 0
let globalArr = []
let lastPageNumber = 0;

allAnn()

// Announcement List - Start
function allAnn(){
    const pageSize = $("#aPage").val()
    //pageCount(1);
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
                            <button  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                           <a ><button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button> </a>
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