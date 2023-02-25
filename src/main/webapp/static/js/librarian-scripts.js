function openConfirmOrderModal(orderCode, isValuable) {
    confirmOrderCodeInput.value = orderCode;
    confirmOrderDeadlineDateInput.hidden = isValuable;
    confirmOrderDeadlineDateInput.value = "";
    $('#confirmOrderModal').modal('show');
}

function openCancelOrderModal(orderCode) {
    cancelOrderCodeInput.value = orderCode;
    $('#cancelOrderModal').modal('show');
}

function openCloseOrderModal(orderCode) {
    closeOrderCodeInput.value = orderCode;
    $('#closeOrderModal').modal('show');
}