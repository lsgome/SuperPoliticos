/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SuperPoliticosTestModule } from '../../../test.module';
import { PoliticoAtributoSpComponent } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.component';
import { PoliticoAtributoSpService } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.service';
import { PoliticoAtributoSp } from '../../../../../../main/webapp/app/entities/politico-atributo-sp/politico-atributo-sp.model';

describe('Component Tests', () => {

    describe('PoliticoAtributoSp Management Component', () => {
        let comp: PoliticoAtributoSpComponent;
        let fixture: ComponentFixture<PoliticoAtributoSpComponent>;
        let service: PoliticoAtributoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [PoliticoAtributoSpComponent],
                providers: [
                    PoliticoAtributoSpService
                ]
            })
            .overrideTemplate(PoliticoAtributoSpComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PoliticoAtributoSpComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoliticoAtributoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PoliticoAtributoSp(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.politicoAtributos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
