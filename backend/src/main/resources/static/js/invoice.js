let items = [];

async function loadSkus() {
  const res = await fetch('/invoice/skus');
  const data = await res.json();
  const sel = document.getElementById('sku');

  data.forEach(s => {
    const o = document.createElement('option');
    o.value = s.sku;
    o.text = `${s.sku} - ${s.name}`;
    o.dataset.price = s.price;
    sel.appendChild(o);
  });
}

function addItem() {
  const sel = sku.selectedOptions[0];
  const qty = Number(document.getElementById('qty').value);

  items.push({
    sku: sel.value,
    name: sel.text.split(' - ')[1],
    qty: qty,
    price: Number(sel.dataset.price)
  });

  render();
}

function render() {
  let sub = 0;
  itemsBody = document.getElementById('items');
  itemsBody.innerHTML = '';

  items.forEach((i, idx) => {
    const amt = i.qty * i.price;
    sub += amt;

    itemsBody.innerHTML += `
      <tr>
        <td>${i.sku}</td>
        <td>${i.name}</td>
        <td>${i.qty}</td>
        <td>${i.price}</td>
        <td>${amt}</td>
        <td><button onclick="removeItem(${idx})">✖</button></td>
      </tr>`;
  });

  const taxRate = Number(document.getElementById('taxRate').value);
  const tax = sub * taxRate / 100;

  document.getElementById('subTotal').innerText = sub.toFixed(2);
  document.getElementById('taxAmt').innerText = tax.toFixed(2);
  document.getElementById('grandTotal').innerText = (sub + tax).toFixed(2);
}

function removeItem(i) {
  items.splice(i, 1);
  render();
}

function generatePDF() {
  fetch('/invoice/pdf', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({
      billTo: billTo.value,
      soldTo: soldTo.value,
      gstNumber: gst.value,
      poNumber: po.value,
      salesPerson: sales.value,
      date: date.value,
      taxRate: Number(taxRate.value),
      items: items
    })
  })
  .then(r => r.blob())
  .then(b => {
    const a = document.createElement('a');
    a.href = URL.createObjectURL(b);
    a.download = 'invoice.pdf';
    a.click();
  });
}

loadSkus();
