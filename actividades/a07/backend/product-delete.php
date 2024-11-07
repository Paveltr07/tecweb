<?php
namespace Backend;
include_once __DIR__.'/Products.php';
$product = new Products();
$product->delete($_POST['id']);  
echo $product->getData();
?>