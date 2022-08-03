var tag_process;
function display_tag(select){
// tạo blur + click ra ngoài
    document.querySelector('.click_out').style.display = 'block'
    document.querySelector('.home').style.filter = 'blur(10px)'
}
function creat_task(){
    let create_tag = document.querySelector('.create')
    create_tag.style.top = '20px';
    create_tag.style.opacity = '0.8';
// tạo blur + click ra ngoài
    document.querySelector('.click_out').style.display = 'block'
    document.querySelector('.home').style.filter = 'blur(10px)'

}
function wayBackHome() {
    document.querySelector('.click_out').style.display = 'none';
    document.querySelector('.home').style.filter = 'blur(0px)';

    document.querySelector('.create').style.top = '-1000px';
    document.querySelector('.create').opacity = '0';

    document.querySelector('.delete').style.top = '-1000px';
    document.querySelector('.delete').opacity = '0';

    document.querySelector('.action_edit').style.top = '-1000px';
    document.querySelector('.action_edit').opacity = '0';

    document.querySelector('.detailTag').style.top = '-1000px';
    document.querySelector('.detailTag').opacity = '0';
}
function GenerateTaskDetails(id,title,content,status) {
    let detailTag = document.querySelector('.detailTag')
    detailTag.style.top = '20px';
    detailTag.style.opacity = '0.8';
    document.querySelector('.click_out').style.display = 'block'
    document.querySelector('.home').style.filter = 'blur(10px)'

    detailTag.querySelector('h1').innerHTML = title
    detailTag.querySelector('p').innerHTML = content
    let btns = detailTag.querySelectorAll('button')
    console.log(btns)
    btns[0].setAttribute('onclick','wayBackHome()')
    title = "'" + title + "'"
    content = "'" + content + "'"
    btns[1].setAttribute('onclick',`GenerateTaskEdit(${id},${title},${content},${status})`)
    btns[2].setAttribute('onclick',`GenerateTaskDelete(${id},${title})`)

}
function GenerateTaskEdit(id,title,content,status) {
    document.querySelector('.detailTag').style.top = '-1000px';
    document.querySelector('.detailTag').opacity = '0';
    let edit_tag = document.querySelector('.action_edit')
    edit_tag.style.top = '20px';
    edit_tag.style.opacity = '0.8';
    let form = document.querySelector('.action_edit form').setAttribute('action',"/tasks/edit/"+id)
    document.querySelector('.click_out').style.display = 'block'
    document.querySelector('.home').style.filter = 'blur(10px)'

    edit_tag.querySelector('input').value = title
    edit_tag.querySelector('textarea').value = content
}
function GenerateTaskDelete(id,title) {
    document.querySelector('.detailTag').style.top = '-1000px';
    document.querySelector('.detailTag').opacity = '0';
    let delete_tag = document.querySelector('.delete')
    delete_tag.style.top = '20px';
    delete_tag.style.opacity = '0.8';
    let form = document.querySelector('.delete')
    form.querySelector('h1').innerHTML = title
    form.querySelector('form').setAttribute('action',"/tasks/delete/"+id)

    document.querySelector('.click_out').style.display = 'block'
    document.querySelector('.home').style.filter = 'blur(10px)'
}
function submitform(){
    document.querySelector('.home form').submit()
}