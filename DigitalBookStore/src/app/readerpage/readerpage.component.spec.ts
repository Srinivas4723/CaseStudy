import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderpageComponent } from './readerpage.component';

describe('ReaderpageComponent', () => {
  let component: ReaderpageComponent;
  let fixture: ComponentFixture<ReaderpageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReaderpageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReaderpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
