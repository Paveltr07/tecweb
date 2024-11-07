<?php
namespace Backend;
include_once __DIR__.'/Products.php';
$product = new Products();
$product->list();
echo $product->getData();
?>