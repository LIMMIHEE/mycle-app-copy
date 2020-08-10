const express = require('express');
const http = require('http');
const bodyparser = require('body-parser');
const app = express();
const fs = require('fs');

app.set('port',process.env.PORT || 3000);
app.use(bodyparser.urlencoded({
    extended:false
}));
app.use(bodyparser.json());




app.use((req,res,next)=>{
    
    //var correct  = {'correct_id' : 'NO','correct_pw' : 'NO'};
    var Id = req.body.id;
    var Pw = req.body.pw;
    console.log('아이디 : '+Id+' 비밀번호 : '+Pw);

    fs.readFile('/memberList.json', 'utf8', function(err, data){
        if (err){
            console.log(err);
        } else {
        obj = JSON.parse(data); 
        obj.member.Push({id: Id, pw:Pw}); 
        json = JSON.stringify(obj); 
        fs.writeFile('/memberList.json', json, 'utf8', callback);
        }
    });
    // if(Id =='test') correct.correct_id='OK';
    // if(Pw == 'test01') correct.correct_pw='OK';
    console.log('저장 완료');
    ///res.send(correct);

    
})

var server = http.createServer(app).listen(app.get('port'),function () {
    console.log('웹 서버 실행 || '+app.get('port'));
});