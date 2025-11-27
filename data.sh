#!/usr/bin/env bash
  set -euo pipefail

  BASE_URL="http://localhost:8080/api"
  HDR_CT="Content-Type: application/json"

  echo "==> Creando CLIENTES"
  c1=$(curl -s -X POST "$BASE_URL/clientes" -H "$HDR_CT" -d '{
    "nombre": "Sara Gonzalez Londono",
    "email": "sara.londono@example.com",
    "telefono": "+57 312 555 0142",
    "direccion": "Calle 82 #12-50, Medellín"
  }' | jq -r '.data.id')

  c2=$(curl -s -X POST "$BASE_URL/clientes" -H "$HDR_CT" -d '{
    "nombre": "Camila Restrepo",
    "email": "camila.restrepo2@example.com",
    "telefono": "+57 301 555 9801",
    "direccion": "Carrera 7 # 72-41, Bogotá"
  }' | jq -r '.data.id')

  c3=$(curl -s -X POST "$BASE_URL/clientes" -H "$HDR_CT" -d '{
    "nombre": "Valentina Mora",
    "email": "valentina.mora2@example.com",
    "telefono": "+57 320 555 2234",
    "direccion": "Av. 4N # 34-10, Cali"
  }' | jq -r '.data.id')

  c4=$(curl -s -X POST "$BASE_URL/clientes" -H "$HDR_CT" -d '{
    "nombre": "Laura Perez Rios",
    "email": "laura.perez.rios2@example.com",
    "telefono": "+57 315 555 6677",
    "direccion": "Transv. 25 # 45-60, Bogotá"
  }' | jq -r '.data.id')

  echo "Clientes: c1=$c1 c2=$c2 c3=$c3 c4=$c4"

  echo "==> Creando PRODUCTOS"
  p1=$(curl -s -X POST "$BASE_URL/productos" -H "$HDR_CT" -d '{
    "sku": "LIPS-MATTE-ROSE-01",
    "nombre": "Labial Matte Rose",
    "descripcion": "Labial mate de larga duración",
    "precio": 39900,
    "stock": 120,
    "activo": true
  }' | jq -r '.data.id')

  p2=$(curl -s -X POST "$BASE_URL/productos" -H "$HDR_CT" -d '{
    "sku": "BASE-LIQ-NEUTRA-01",
    "nombre": "Base Líquida Neutra",
    "descripcion": "Cobertura media, acabado natural",
    "precio": 74900,
    "stock": 80,
    "activo": true
  }' | jq -r '.data.id')

  p3=$(curl -s -X POST "$BASE_URL/productos" -H "$HDR_CT" -d '{
    "sku": "MASK-VOL-NEGRO-01",
    "nombre": "Máscara Volumen Negra",
    "descripcion": "Resistente al agua",
    "precio": 45900,
    "stock": 150,
    "activo": true
  }' | jq -r '.data.id')

  p4=$(curl -s -X POST "$BASE_URL/productos" -H "$HDR_CT" -d '{
    "sku": "RUBOR-DUO-CORAL-01",
    "nombre": "Rubor Duo Coral",
    "descripcion": "Duo coral/rosa",
    "precio": 32900,
    "stock": 90,
    "activo": true
  }' | jq -r '.data.id')

  p5=$(curl -s -X POST "$BASE_URL/productos" -H "$HDR_CT" -d '{
    "sku": "DELINEADOR-LIQ-NEGRO-01",
    "nombre": "Delineador Líquido Negro",
    "descripcion": "Punta fina, larga duración",
    "precio": 28900,
    "stock": 200,
    "activo": true
  }' | jq -r '.data.id')

  echo "Productos: p1=$p1 p2=$p2 p3=$p3 p4=$p4 p5=$p5"

  echo "==> Creando VENTAS"
  # Venta 1: Sara compra 2 labiales y 1 base
  curl -s -X POST "$BASE_URL/ventas" -H "$HDR_CT" -d "{
    \"clienteId\": $c1,
    \"items\": [
      { \"productoId\": $p1, \"cantidad\": 2 },
      { \"productoId\": $p2, \"cantidad\": 1 }
    ]
  }" | jq '.'

  # Venta 2: Camila compra 1 máscara y 1 delineador
  curl -s -X POST "$BASE_URL/ventas" -H "$HDR_CT" -d "{
    \"clienteId\": $c2,
    \"items\": [
      { \"productoId\": $p3, \"cantidad\": 1 },
      { \"productoId\": $p5, \"cantidad\": 1 }
    ]
  }" | jq '.'

  # Venta 3: Laura compra 1 base y 1 rubor
  curl -s -X POST "$BASE_URL/ventas" -H "$HDR_CT" -d "{
    \"clienteId\": $c4,
    \"items\": [
      { \"productoId\": $p2, \"cantidad\": 1 },
      { \"productoId\": $p4, \"cantidad\": 1 }
    ]
  }" | jq '.'

  echo "==> Listado de ventas:"
  curl -s "$BASE_URL/ventas" | jq '.'