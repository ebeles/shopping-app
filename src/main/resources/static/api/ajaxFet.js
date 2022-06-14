function handleListProduct(event)
{
event.preventDefault();
fire_ajax1();
}

function fire_ajax1()
{
    $.ajax({
    type: "GET",
    //enctype: multipart
    url: "/listProduct",
    processData: false,
    contentType: false,
    cache: false,
    timeout: 600000,
    success: function (data) {
    $("#app").empty();
    $("#app").append(data);
    },
    error: function(e) {
    console.log("ERROR : ", e);
    }
    });
}

function handleAddProduct(event)
{
event.preventDefault();
fire_ajax();
}

function fire_ajax()
{
    $.ajax({
    type: "GET",
    //enctype: multipart
    url: "/addProduct",
    processData: false,
    contentType: false,
    cache: false,
    timeout: 600000,
    success: function (data) {
    $("#app").empty();
    $("#app").append(data);
    },
    error: function(e) {
    console.log("ERROR : ", e);
    }
    });
}