/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { LetterDetailComponent } from 'app/entities/letter/letter-detail.component';
import { Letter } from 'app/shared/model/letter.model';

describe('Component Tests', () => {
    describe('Letter Management Detail Component', () => {
        let comp: LetterDetailComponent;
        let fixture: ComponentFixture<LetterDetailComponent>;
        const route = ({ data: of({ letter: new Letter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LetterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LetterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.letter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
