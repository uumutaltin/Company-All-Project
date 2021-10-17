
function updateCustomer() {
    const cid = ucid;
    const cname = $("#cname").val()
    const csurname = $("#csurname").val()
    const mobile_phone = $("#mobile_phone").val()
    const email = $("#email").val()
    const tax = $("#tax").val()
    const tax_administration = $("#tax_administration").val()
    const ctype = $("#ctype").val()
    const cNote = $("#cnote").val()
    const cProvince = $("#cprovince").val()
    const cDistrict = $("#cdistrict").val()
    const cAddress = $("#caddress").val()
    const cDiscount = $("#cdiscount").val()


    const obj = {
        cid: cid,
        cname: cname,
        csurname: csurname,
        mobile_phone: mobile_phone,
        email: email,
        tax: tax,
        tax_administration: tax_administration,
        ctype: ctype,
        cnote: cNote,
        cprovince: cProvince,
        cdistrict: cDistrict,
        caddress: cAddress,
        cdiscount: cDiscount
    }
    console.log(obj);

    $.ajax({
        url: './update',
        type: 'POST',
        data: JSON.stringify(obj),
        dataType: 'JSON',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data) {
                $("#sname").val("")
                $("#semail").val("")
                $("#sphone").val("")
                console.log(data)
                allCustomer()
            } else {
                console.log("İşlem sırasında hata oluştu!")
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })

}