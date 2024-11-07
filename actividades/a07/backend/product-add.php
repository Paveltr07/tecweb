<?php
namespace Backend;
include_once __DIR__.'/Products.php';
$product = new Products();
$product->add($_POST);  
echo $product->getData();
?>