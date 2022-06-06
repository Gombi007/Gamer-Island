import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageStoreComponent } from './page-store.component';

describe('PageStoreComponent', () => {
  let component: PageStoreComponent;
  let fixture: ComponentFixture<PageStoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageStoreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PageStoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
