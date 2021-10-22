let globalGalArr = []



fncGalleryList();

// Galleries List - start
function fncGalleryList(){
    $.ajax({
        url: '/gallery/list',
        type: 'GET',
        success: function (data){
            console.log(data)
            createGalleries(data)
        },
        error: function (err){
            console.log(err)
        }
    })
}

let gAll = $("#gAll");
function createGalleries(data) {
    const id = $("#galleryID").val();

    gAll.find('option').remove().end();
    globalGalArr = data;
    for (let i = 0; i<data.length;i++){
        const itm = data[i];
        const st = itm.gname
        if(itm.gid == id){
            gAll.append(`<option selected data-subtext="" value="`+itm.gid+`">`+st+`</option>`)
        }else {
            gAll.append(`<option data-subtext="" value="`+itm.gid+`">`+st+`</option>`)
        }

    }
    gAll.selectpicker("refresh");
}
// Galleries List - end