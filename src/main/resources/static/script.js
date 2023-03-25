getAllProducts();

function getAllProducts() {

    const requestOptions = {
        method: "GET",
        redirect: "follow"
    }

    fetch("http://localhost:8080/products", requestOptions)
        .then(response => response.json())
        .then(products => {

            document.getElementById("productList").innerText = "";

            products.forEach((product) => {

                const newProduct = document.createElement("div");
                newProduct.setAttribute("id", "product" + product.id);
                document.getElementById("productList").appendChild(newProduct);

                const id = document.createElement("label");
                id.innerText = product.id;
                id.setAttribute("class", "id");
                newProduct.appendChild(id);

                const partNo = document.createElement("label");
                partNo.innerText = product.partNo;
                partNo.setAttribute("class", "partNo");
                newProduct.appendChild(partNo);

                const name = document.createElement("label");
                name.innerText = product.name;
                name.setAttribute("class", "name");
                newProduct.appendChild(name);

                const description = document.createElement("label");
                if (product.description !== "null") description.innerText = product.description;
                else description.innerText = "";
                description.setAttribute("class", "description");
                newProduct.appendChild(description);

                const forSale = document.createElement("label");
                forSale.innerText = product?.forSale? "ANO" : "NE";
                forSale.setAttribute("class", "forSale");
                newProduct.appendChild(forSale);

                const price = document.createElement("label");
                price.innerText = Math.round(product.price).toLocaleString('cs-CZ') + " Kč";
                price.setAttribute("class", "price");
                price.setAttribute("id", "price" + product.id);
                newProduct.appendChild(price);

                const deleteButton = document.createElement("label");
                deleteButton.innerText = "Odstranit produkt";
                deleteButton.setAttribute("class", "button deleteProduct");
                deleteButton.setAttribute("onclick", "deleteProduct(" + product.id + ")");
                newProduct.appendChild(deleteButton);

                const insertPriceButton = document.createElement("label");
                insertPriceButton.innerText = "Aktualizovat cenu";
                insertPriceButton.setAttribute("class", "button insertProductPrice");
                insertPriceButton.setAttribute("id", "insertProductPrice" + product.id);
                insertPriceButton.setAttribute("onclick", "insertProductPrice(" + product.id + ")");
                newProduct.appendChild(insertPriceButton);

            });

        })

        .catch(error => console.log("error", error));

}

function getProduct() {

    const requestOptions = {
        method: "GET",
        redirect: "follow"
    }

    const id = document.getElementById("idInput").value;

    if (id > 0) {

        fetch("http://localhost:8080/products/" + id, requestOptions)
            .then(response => response.json())
            .then(result => {

                if (result.id) {

                    document.getElementById("getId").textContent = result.id;

                    document.getElementById("getPartNo").textContent = result.partNo;

                    document.getElementById("getName").textContent = result.name;

                    if (result.description !== "null") document.getElementById("getDescription").textContent = result.description;
                    else document.getElementById("getDescription").textContent = "";

                    if (result.forSale) document.getElementById("getForSale").textContent = "ANO";
                    else document.getElementById("getForSale").textContent = "NE";

                    document.getElementById("getPrice").textContent = Math.round(result.price).toLocaleString('cs-CZ') + " Kč";

                } else alert(result.message + "\n" + result.timeStamp);

            })

            .catch(error => console.log("error", error));

    } else alert("Je nutné zadat celé kladné číslo!");

}

function saveProduct() {

    document.getElementById("partNo").style.color = "white";
    document.getElementById("name").style.color = "white";
    document.getElementById("price").style.color = "white";
    document.getElementById("partNoNote").style.display = "block";
    document.getElementById("partNoNote").innerText = "* požadovaný údaj";
    document.getElementById("partNoNote").style.color = "#212323";
    document.getElementById("nameNote").innerText = "";
    document.getElementById("priceNote").innerText = "";

    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const forSale = document.getElementById("setForSale").value;
    let setForSale;
    if (forSale === "1") setForSale = true;
    else setForSale = false;

    const partNo = document.getElementById("setPartNo").value;
    const name = document.getElementById("setName").value;
    const price = document.getElementById("setPrice").value;

    const raw = JSON.stringify({
        "partNo": partNo,
        "name": name,
        "description": document.getElementById("setDescription").value,
        "forSale": setForSale,
        "price": price
    });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    }

    if (partNo > 0 && name !== "" && price > 0) {

        fetch("http://localhost:8080/products", requestOptions)
            .then(response => response.text())
            .then(() => {

                getAllProducts();

            })

            .catch(error => console.log("error", error));

    } if (name === "") {
        document.getElementById("partNoNote").style.display = "none";
        document.getElementById("nameNote").innerText = "Musí být vyplněno!";
        document.getElementById("name").style.color = "red";

    } if (!(price > 0)) {
        document.getElementById("partNoNote").style.display = "none";
        document.getElementById("priceNote").innerText = "Musí být vyplněné kladné číslo!";
        document.getElementById("price").style.color = "red";

    } if (!(partNo > 0)) {
        document.getElementById("partNoNote").style.display = "block";
        document.getElementById("partNoNote").innerText = "Musí být vyplněné celé kladné číslo!";
        document.getElementById("partNoNote").style.color = "red";
        document.getElementById("partNo").style.color = "red";
    }

}

function insertProductPrice(id) {

    const updateButton = document.getElementById("insertProductPrice" + id);
    updateButton.innerText = "Uložit";
    updateButton.setAttribute("onclick", "updateProductPrice(" + id + ")");

    const oldPrice = document.getElementById("price" + id).innerText;

    const product = document.getElementById("product" + id);

    const inputPrice = document.createElement("input");
    inputPrice.setAttribute("class", "price setNewPrice");
    inputPrice.setAttribute("id", "setNewPrice" + id);
    inputPrice.setAttribute("type", "text");
    inputPrice.setAttribute("placeholder", oldPrice);
    product.appendChild(inputPrice);

}

function updateProductPrice(id) {

    const requestOptions = {
        method: "PATCH",
        redirect: "follow"
    }

    const price = document.getElementById("setNewPrice" + id).value;

    if (price > 0) {

        fetch("http://localhost:8080/products/" + id + "?price=" + price, requestOptions)
            .then(response => response.text())
            .then(() => {

                getAllProducts();

            })

            .catch(error => console.log("error", error));

    } else alert("Je nutné zadat kladné číslo!");

}

function deleteProductsNotForSale() {

    const requestOptions = {
        method: "DELETE",
        redirect: "follow"
    }

    fetch("http://localhost:8080/products", requestOptions)
        .then(response => response.text())
        .then(() => {

            getAllProducts();

        })

        .catch(error => console.log("error", error));

}

function deleteProduct(id) {

    const requestOptions = {
        method: "DELETE",
        redirect: "follow"
    }

    fetch("http://localhost:8080/products/" + id, requestOptions)
        .then(response => response.text())
        .then(() => {

            getAllProducts();

        })

        .catch(error => console.log("error", error));

}