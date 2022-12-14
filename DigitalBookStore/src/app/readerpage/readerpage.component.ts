import { applySourceSpanToStatementIfNeeded } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-readerpage',
  templateUrl: './readerpage.component.html',
  styleUrls: ['./readerpage.component.css']
})
export class ReaderpageComponent implements OnInit {
  cardnumberblankResponse: string="";
  constructor(public userService:UserService,public router:Router) { }
  book:any=this.userService.book;
  readerFormFlag=true;
  buyBookContainerFlag=true;
  cardnumber:string="";
  reader={
    readername:"",
    readeremail:"",
    bookid:this.userService.book.id
  }
  readerblankResponse:any={
    readername:"",
    readeremail:""
  }
  bookPurchaseSuccessMessage:any;
  bookPurchaseFailureMessage:any;
  makepayment(){
    this.cardnumberblankResponse="";
    this.readerblankResponse.readername="";
    this.readerblankResponse.readeremail="";
    this.bookPurchaseFailureMessage="";
    this.bookPurchaseSuccessMessage="";
    let errorcount=0;
    if(this.cardnumber==="" ){
      this.cardnumber="";
      errorcount+=1;
      this.cardnumberblankResponse="Card Number cannot be Blank";
    }
    if(this.reader.readername==="" || this.reader.readername===null || this.reader.readername.trim()===""){
      this.reader.readername="";
      errorcount+=1;
      this.readerblankResponse.readername="Reader Name Cannot be Blank";
    }
    if(this.reader.readeremail==="" || this.reader.readeremail.trim()===""){
      this.reader.readeremail="";
      errorcount+=1;
      this.readerblankResponse.readeremail="Reader Email Cannot be Blank";
    }
    if(errorcount===0){
      const observable=this.userService.buybook(this.reader);
      observable.subscribe((responseBody)=>{
        
        this.readerblankResponse=responseBody;
      },
      (error:any)=>{
        console.log("R"+JSON.stringify(error.error));
        if(typeof error.error.text==='string'){
          this.readerFormFlag=false;
          this.bookPurchaseSuccessMessage=error.error.text;
          
        }
        else if(typeof error.error==="string" && error.error.includes("Valid Email")){
          this.readerblankResponse.readeremail=error.error;
        }
        else{
          this.reader.readername="";
          this.reader.readeremail="";
          this.cardnumber="";
        this.bookPurchaseFailureMessage=error.error;
        }
      });
    }
  }
  cancelpurchase(){
    this.userService.digitalBooksContainerFlag=true;
    this.userService.slideShowFlag=true;
    this.router.navigate(["/"]);
  }
  
  ngOnInit(): void {   
  }
}
