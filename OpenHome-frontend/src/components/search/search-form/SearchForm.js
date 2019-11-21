import React, { Component } from 'react';
import serialize from 'form-serialize';


class SearchForm extends Component {
    
    constructor(props){
        super(props);
        
    }
    

    onFormSubmit = (e)=>{
        e.preventDefault();
        var form = serialize(e.target, { hash: true });
        this.props.onSave(form);
    }

    

    render() { 
        const searchCriteria = this.props.fields;
        
        return (
            <form onSubmit={this.onFormSubmit}>
                
                <div className="row" style={{border:'1px solid rgba(0,0,0,.0)',padding:'-2em'}}>
                    <div className="col-md-1 mt-2 mb-2"></div>

                    <div className="col-md-3 mt-2 mb-2">
                        <div className="inner-child add-border-search-form" >
                            <label className="pl-1 pl-2" style={{ fontSize: '13px', bottom: '0px', color: '#A4AC9D', }}>Where</label>
                            <input className="ml-1 pl-2 mt-0 remove-bg-input" type="text" name="city" defaultValue={searchCriteria ? searchCriteria.city : ""} style={{ width: '90%', fontSize: '18px', lineHeight: '1.0rem',color:'black' }} placeholder="Where to?" />
                        </div>
                    </div>

                    <div className="col-md-2 mt-2 mb-2">
                        <div className="add-border-search-form" style={{ width: '100%', height: '100%' }}>
                            <div className="mt-2 mb-2 pt-1">
                                <img className="ml-1 mt-2" width="20px" height="20px" src="https://png.icons8.com/ios/64/cccccc/calendar-filled.png" style={{ verticalAlign: 'middle' }} />
                                <input className="ml-1 mt-2 remove-bg-input" type="date" name="startdate" defaultValue={searchCriteria ? searchCriteria.startdate : ""} style={{ width: '75%', fontSize: '14px', lineHeight: '1.5rem', verticalAlign: 'middle',color:'black' }} />
                            </div>

                        </div>
                    </div>

                    <div className="col-md-2 mt-2 mb-2">
                        <div className="add-border-search-form" style={{ width: '100%', height: '100%' }}>
                            <div className="mt-2 mb-2 pt-1">
                                <img className="ml-1 mt-2" width="20px" height="20px" src="https://png.icons8.com/ios/64/cccccc/calendar-filled.png" style={{ verticalAlign: 'middle' }} />
                                <input className="ml-1 mt-2 remove-bg-input" type="date" name="enddate" defaultValue={searchCriteria ? searchCriteria.enddate : ""} style={{ width: '75%', fontSize: '14px', lineHeight: '1.5rem', verticalAlign: 'middle',color:'black' }} />
                            </div>

                        </div>
                    </div>

                    <div className="col-md-2 mt-2 mb-2">
                        <div className="inner-child add-border-search-form" >
                            <label className="ml-1 pl-2" style={{ fontSize: '13px', bottom: '0px', color: '#A4AC9D' }}>Guests</label>
                            <input className="ml-1 pl-2 mt-0 remove-bg-input" type="number" name="accomodate" defaultValue={searchCriteria ? searchCriteria.accomodate : ""}  style={{ width: '90%', fontSize: '18px', lineHeight: '1.0rem',color:'black' }} placeholder="2" />
                        </div>
                    </div>


                    <div className="col-md-2 mt-2 mb-2">

                        <button className="btn btn-primary btn-lg mt-3" type="submit">SUBMIT</button>

                    </div>

                </div>
            </form>
        );
    }
}
/*
const mapStateToProps =(state) =>{
    return{
        searchCriteria: state.searchFieldsHome
    }
}

const mapDispatchToProps = (dispatch) =>{
    return {
        updateSearchFields : (searchFields)=>{
            dispatch({
              type:SEARCH_FIELDS,
              payload:searchFields
            });
        }
    }
}
*/
export default SearchForm;
//export default connect(mapStateToProps,mapDispatchToProps)(SearchForm);