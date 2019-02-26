/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { LetterBookDetailComponent } from 'app/entities/letter-book/letter-book-detail.component';
import { LetterBook } from 'app/shared/model/letter-book.model';

describe('Component Tests', () => {
    describe('LetterBook Management Detail Component', () => {
        let comp: LetterBookDetailComponent;
        let fixture: ComponentFixture<LetterBookDetailComponent>;
        const route = ({ data: of({ letterBook: new LetterBook(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterBookDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LetterBookDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LetterBookDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.letterBook).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
