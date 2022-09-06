import { applySourceSpanToStatementIfNeeded } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-readerpage',
  templateUrl: './readerpage.component.html',
  styleUrls: ['./readerpage.component.css']
})
export class ReaderpageComponent implements OnInit {

  constructor(public userSerive:UserService) { }
  book:any=this.userSerive.book;
  reader={
    readername:"",
    readeremail:"",
    bookid:this.book.id
  }
  readerblankResponse:any={
    readername:"",
    readeremail:""
  }
  bookPurchaseSuccessMessage1:any;
  bookPurchaseSuccessMessage:any;
  bookPurchaseFailureMessage:any;
  makepayment1(){
    this.bookPurchaseSuccessMessage=this.bookPurchaseSuccessMessage1;
    const cardDetails:any=document.getElementById("cardDetails");
    cardDetails.style.display="none";
  }
  makepayment(){
    this.bookPurchaseFailureMessage="";
    this.bookPurchaseSuccessMessage="";
    const observable=this.userSerive.buybook(this.reader);
    observable.subscribe((responseBody)=>{
      console.log("E"+JSON.stringify(responseBody));
      this.readerblankResponse=responseBody;
    },
    (error:any)=>{
      console.log("R"+JSON.stringify(error.error));
      if(typeof error.error.text==='string'){
        
        this.bookPurchaseSuccessMessage1=error.error.text.replace("\n","\r\n");
        const buybookContainer:any=document.getElementById("buybookContainer");
          buybookContainer.style.display="none";
          const cardDetails:any=document.getElementById("cardDetails");
        cardDetails.style.display="block";
      }
      else{
      this.bookPurchaseFailureMessage=error.error;
      }
    })

  }
  ngOnInit(): void {
    const digitalBooksContainer:any=document.getElementById("digitalBooksContainer");
    digitalBooksContainer.style.display="none";
    const cardDetails:any=document.getElementById("cardDetails");
        cardDetails.style.display="none";
    console.log("b"+this.book);
  }

}
